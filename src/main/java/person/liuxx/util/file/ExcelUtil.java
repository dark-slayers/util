package person.liuxx.util.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Excel工具（使用POI进行Excel文件解析）
 * 
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午3:36:36
 * @since 1.0.0
 */
public final class ExcelUtil
{
    private ExcelUtil()
    {
        throw new AssertionError("工具类禁止实例化！");
    }

    /**
     * 判断单元格（使用POI解析Excel文件得到的Cell对象）的值是否为指定字符串
     * <p>
     * 符合下列全部条件时返回true：<br>
     * 1、单元格对象不为<em>null</em><br>
     * 2、单元格的类型为<strong>CellType.STRING</strong><br>
     * 3、 单元格的值<mark><strong>忽略前后空白</strong></mark>与给定字符串一致
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午1:06:36
     * @since 1.0.0
     * @param cell
     *            使用POI解析Excel文件中的单元格得到的Cell对象
     * @param text
     *            需要比对的字符串
     * @return 单元格的内容是否为指定字符串
     */
    public static boolean cellTextEquals(Cell cell, String text)
    {
        return Objects.nonNull(cell) && Objects.equals(cell.getCellTypeEnum(), CellType.STRING)
                && Objects.equals(cell.getStringCellValue().trim(), text);
    }

    public static Row stringToRow(Sheet sh, int rowNum, String line)
    {
        Row row = sh.createRow(rowNum);
        String[] cellArray = line.split("\t");
        for (int cellnum = 0, max = cellArray.length; cellnum < max; cellnum++)
        {
            Cell cell = row.createCell(cellnum);
            cell.setCellValue(cellArray[cellnum]);
        }
        return row;
    }

    public static void txtToExcel(Path txtFilePath, String excelFilePath) throws IOException
    {
        try (SXSSFWorkbook wb = new SXSSFWorkbook(100);
                FileOutputStream out = new FileOutputStream(excelFilePath);)
        {
            List<String> list = Files.readAllLines(txtFilePath);
            Sheet sh = wb.createSheet();
            for (int i = 0, max = list.size(); i < max; i++)
            {
                stringToRow(sh, i, list.get(i));
            }
            wb.write(out);
            wb.dispose();
        }
    }

    /**
     * 将单元格视为数值格式，获取整型数值的Optional,如果获取失败，返回空Optional
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午11:48:40
     * @since 1.0.0
     * @param cell
     * @return
     */
    public static Optional<Integer> getCellInteger(Cell cell)
    {
        Optional<Integer> result = Optional.ofNullable(cell).map(c ->
        {
            c.setCellType(CellType.NUMERIC);
            return (int) cell.getNumericCellValue();
        });
        return result;
    }

    /**
     * 将单元格视为数值格式，获取Double型数值的Optional,如果获取失败，返回空Optional
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午11:48:40
     * @since 1.0.0
     * @param cell
     * @return
     */
    public static Optional<Double> getCellDouble(Cell cell)
    {
        Optional<Double> result = Optional.ofNullable(cell).map(c ->
        {
            c.setCellType(CellType.NUMERIC);
            return cell.getNumericCellValue();
        });
        return result;
    }

    /**
     * 将单元格视为文本格式，获取字符串的Optional,如果获取失败，返回空Optional
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午3:44:03
     * @since 1.4.1
     * @param cell
     * @return
     */
    public static Optional<String> getCellText(Cell cell)
    {
        Optional<String> result = Optional.ofNullable(cell).map(c ->
        {
            c.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        });
        return result;
    }

    /**
     * 将单元格视为日期格式，获取LocalDate值的Optional,如果获取失败，返回空Optional
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午4:16:09
     * @since 1.0.0
     * @param cell
     * @return
     */
    public static Optional<LocalDate> getCellDate(Cell cell)
    {
        Optional<LocalDate> result = Optional.ofNullable(cell)
                .map(c -> getDate(c))
                .map(d -> d.toInstant())
                .map(i -> i.atZone(ZoneId.of("Asia/Shanghai")))
                .map(z -> z.toLocalDate());
        return result;
    }

    private static Date getDate(Cell cell)
    {
        cell.setCellType(CellType.NUMERIC);
        return cell.getDateCellValue();
    }
}
