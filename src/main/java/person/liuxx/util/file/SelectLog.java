package person.liuxx.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午1:57:45
 * @since 1.0.0
 */
public class SelectLog
{
	/**
	 * 将指定路径的文本文件逐行进行内容过滤，符合过滤条件的内容将会写入新的文件<br>
	 * 新的文件以原文件名后面追加_edit，放置在原文件所在文件夹
	 * 
	 * @author 刘湘湘
	 * @version 1.0.0<br>
	 *          创建时间：2018年4月6日 下午2:23:33
	 * @since 1.0.0
	 * @param path
	 *            需要分析的文件的路径
	 * @param predicate
	 *            过滤规则
	 * @return
	 * @throws IOException
	 */
	public static Path selectFromLogFile(Path path, Predicate<String> predicate) throws IOException
	{
		Objects.requireNonNull(path, "表示日志文件的Path对象不能为null");
		Objects.requireNonNull(predicate, "选择条件不能为null");
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS) || Files.isDirectory(path,
				LinkOption.NOFOLLOW_LINKS))
		{
			throw new IllegalArgumentException("无效的文件路径");
		}
		try (Stream<String> stream = Files.lines(path);)
		{
			Path dir = path.getParent();
			String fileName = path.getFileName().toString();
			int dotIndex = fileName.lastIndexOf(".");
			String prefix = fileName.substring(0, dotIndex);
			String suffix = fileName.substring(dotIndex + 1);
			String newFileName = String.join(".", prefix + "_edit", suffix);
			Path newPath = Paths.get(dir.toString(), newFileName);
			List<String> list = stream.parallel().filter(predicate).collect(Collectors.toList());
			return Files.write(newPath, list);
		}
	}

	/**
	 * 将指定路径的文本文件逐行进行内容过滤，行内如果包含参数中的全部文本，则认为符合条件<br>
	 * 新的文件以原文件名后面追加_edit，放置在原文件所在文件夹
	 * <p>
	 * 此方法调用方法{@link #selectFromLogFile(Path, Predicate)}进行处理
	 * 
	 * @author 刘湘湘
	 * @version 1.0.0<br>
	 *          创建时间：2018年4月6日 下午2:27:09
	 * @since 1.0.0
	 * @param path
	 *            需要被分析的文件路径
	 * @param texts
	 *            字符串数组，数组中的全部内容都出现在文件的某一行，则这一行会被提取
	 * @return
	 * @throws IOException
	 */
	public static Path fileContainsText(Path path, String... texts) throws IOException
	{
		return selectFromLogFile(path, s ->
		{
			Stream<String> textStream = Stream.of(texts);
			return textStream.allMatch(t -> s.contains(t));
		});
	}
}
