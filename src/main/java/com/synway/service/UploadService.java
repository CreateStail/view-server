package com.synway.service;


import com.synway.dao.UploadMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class UploadService {
    @Autowired
    private UploadMapper uploadMapper;
    private static Logger log = LoggerFactory.getLogger(UploadService.class);

    @Value("${file.prefix}")
    private String fielPrefix = "";
    @Value("${file.prefix.mapping}")
    private String filePrefixMapping = "";

    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        log.info("========================上传附件开始=========================");
        String fileName = "";
        BufferedOutputStream out = null;
        Map<String, String> resultMap = new HashMap<>();
        File create_file = null;
        if (!file.isEmpty()) {
            File rootFile = new File(fielPrefix);
            if (!rootFile.exists()) {
                rootFile.mkdir();
            }
            fileName = file.getOriginalFilename();
            create_file = new File(fielPrefix + fileName);
            out = new BufferedOutputStream(new FileOutputStream(create_file));
            out.write(file.getBytes());
            out.flush();
            out.close();
            String path = create_file.getPath();
            resultMap.put("fileName", create_file.getName());
            resultMap.put("filePath", path);
            //path是/usr/local/src/view-service/attachment/
            //如果是windows系统，上传路径一般是 D:\software\nginx_win.rar,所以只需要soft..后的内容
            //否则就默认是linux系统,上传路径为/usr/local/src/view-service/attachment/filename 所以只需要usr以后的内容,并且不需要转换符号
            if (System.getProperty("os.name").startsWith("win")) {
                resultMap.put("fileAddr", filePrefixMapping + path.substring(path.indexOf("\\") + 1).replace("\\", "/"));
            } else {
                resultMap.put("fileAddr", filePrefixMapping + path.substring(1));
            }
        }
        return resultMap;
    }

    public Map<String, Object> uploadImage(MultipartFile file) throws IOException {
        log.info("========================上传(内容)图片开始=========================");
        String fileName = "";
        BufferedOutputStream out = null;
        Map<String, Object> paramsMap = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        File rootFile = new File(fielPrefix + "content");
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        fileName = file.getOriginalFilename();
        File create_file = new File(fielPrefix + "content/" + fileName);
        String path = create_file.getPath();
        paramsMap.put("file_name", create_file.getName());
        paramsMap.put("file_path", path);
        paramsMap.put("file_addr", filePrefixMapping + path.substring(path.indexOf("\\") + 1));
        paramsMap.put("type", 0);
        paramsMap.put("position", 0);
        int result = uploadMapper.saveContentPicture(paramsMap);
        if (result > 0) {
            out = new BufferedOutputStream(new FileOutputStream(create_file));
            out.write(file.getBytes());
            out.flush();
            out.close();
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功");
            if (System.getProperty("os.name").startsWith("win")) {
                resultMap.put("url", filePrefixMapping + path.substring(path.indexOf("\\") + 1).replace("\\", "/"));
            } else {
                resultMap.put("url", filePrefixMapping + path.substring(1));
            }
        } else {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败");
        }
        return resultMap;
    }

    /**
     * TODO
     * 此方法后面改成公共方法
     * 注:这里得到的行数不准确,所以遍历了两边，之后看有没有其他办法,还会读出隐藏列
     *
     * @param templeteFile
     * @return
     */
    public List<Map<String, Object>> listTempleteData(MultipartFile templeteFile) {
        String[] keyArray = {"business_name", "file_name", "file_path"};
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            InputStream in = templeteFile.getInputStream();
            String fileName = templeteFile.getOriginalFilename();
            Workbook workbook = getWorkboot(in, fileName);
            if (workbook == null) {
                throw new Exception("Workbook对象为空!");
            }
            //目前只获取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);
//            sheet.setColumnHidden(15,true);
            //得到总行数

            Row row = sheet.getRow(0);
            int rowNum = sheet.getLastRowNum();
            int columNum = row.getPhysicalNumberOfCells();
            int realRowNum = getRealRowNum(sheet);
            //正文内容应该从第二行开始,第一行为表头的标题
            for (int i = 1; i <= rowNum; i++) {
                row = sheet.getRow(i);
                Map<String, Object> cellValue = new HashMap<>();
                for (int j = 0; j < columNum; j++) {
                    Object obj = getCellFormatValue(row.getCell(j));
                    cellValue.put(keyArray[j], obj);
                }
                list.add(cellValue);
            }
        } catch (Exception e) {
            log.error("模板解析失败:" + e);
        }
        return list;
    }

    public Workbook getWorkboot(InputStream in, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(in);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(in);
        } else {
            throw new Exception("请上传excel文件!");
        }
        return workbook;
    }


    public int getRealRowNum(Sheet sheet) {
        int rowNum = sheet.getLastRowNum() - 1;
        while (rowNum > 0) {
            Row row = sheet.getRow(rowNum + 1);
            if (row != null) {
                for (Cell cell : row) {
                    if (getCellFormatValue(cell) != null) ;
                    return rowNum;
                }
            }
            rowNum--;
        }
        return rowNum;
    }

    //根据Cell类型设置数据
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }


}
