package person.liuxx.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Supplier;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import person.liuxx.util.base.StringUtil;
import person.liuxx.util.service.exception.ExcelParseException;

/**
 * @author 刘湘湘
 * @version 1.4.2<br>
 *          创建时间：2018年12月19日 下午12:01:08
 * @since 1.4.2
 */
public class ExcelUtilTest
{
    private static final String[] LINE_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

    /**
     * @author 刘湘湘
     * @version 1.1.0<br>
     *          创建时间：2018年12月19日 下午12:01:08
     * @since 1.1.0
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * @author 刘湘湘
     * @version 1.1.0<br>
     *          创建时间：2018年12月19日 下午12:01:08
     * @since 1.1.0
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * {@link person.liuxx.util.file.DirUtil#createDirIfNotExists(java.nio.file.Path)}
     * 的测试方法。
     */
    @Test
    public void testCreateDirIfNotExists()
    {
        try (InputStream input = Files.newInputStream(Paths.get(
                "E:/项目/标准应用程序运行统计表_08月报_20190801-20190831_用户+应用程序.xlsx")))
        {
            Workbook workBook = WorkbookFactory.create(input);
            Sheet s = workBook.getSheetAt(0);
            for (int rowIndex = 11;; rowIndex++)
            {
                String drawNumber = stringValue(s, rowIndex, 1);
                if (StringUtil.isEmpty(drawNumber))
                {
                    break;
                }
                String name = stringValue(s, rowIndex, 2);
                System.out.println(name);
            }
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e)
        {
            e.printStackTrace();
        }
    }

    private String stringValue(Sheet s, int r, int c)
    {
        return ExcelUtil.getCellText(s, r, c).orElseThrow(parseException(r, c));
    }

    private Supplier<ExcelParseException> parseException(int rowIndex, int cellIndex)
    {
        return () ->
        {
            throw new ExcelParseException("解析excel出现异常，错误位置：" + getCellPoint(rowIndex, cellIndex));
        };
    }

    private String getCellPoint(int rowIndex, int cellIndex)
    {
        if (cellIndex < LINE_ARRAY.length)
        {
            return "单元格<" + LINE_ARRAY[cellIndex] + (rowIndex + 1) + ">";
        }
        return "第" + (rowIndex + 1) + "行第" + (cellIndex + 1) + "列";
    }
}