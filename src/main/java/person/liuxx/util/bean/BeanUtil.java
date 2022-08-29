package person.liuxx.util.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午3:52:23
 * @since 1.0.0
 */
public final class BeanUtil
{
    private static BeanUtilsBean beanUtilsBean;
    static
    {
        beanUtilsBean = BeanUtilsBean.getInstance();
        beanUtilsBean.getConvertUtils().register(new Converter()
        {
            @Override
            public <T> T convert(Class<T> type, Object value)
            {
                return Optional.of(type)
                        .filter(t -> Objects.equals(t, LocalDate.class) || Objects.equals(t,
                                LocalDateTime.class))
                        .map(t -> cast(t, value))
                        .orElse(null);
            }

            private <T> T cast(Class<T> type, Object value)
            {
                try
                {
                    if (Objects.equals(type, LocalDate.class))
                    {
                        return type.cast(LocalDate.parse(value.toString()));
                    } else if (Objects.equals(type, LocalDateTime.class))
                    {
                        return type.cast(LocalDateTime.parse(value.toString()));
                    } else
                    {
                        return null;
                    }
                } catch (Exception e)
                {
                    return null;
                }
            }
        }, LocalDate.class);
    }

    private BeanUtil()
    {
        throw new AssertionError("工具类禁止实例化！");
    }

    /**
     * 复制属性，将orig对象的属性复制到dest，如果两个对象的字段名字相同，但是一个为String，另一个为LocalDate,会进行转换
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午11:47:58
     * @since 1.0.0
     * @param dest
     *            需要被复制属性的目标对象
     * @param orig
     *            需要复制属性的源对象
     */
    public static void copyProperties(final Object dest, final Object orig)
    {
        Predicate<String> predicate = new Predicate<String>()
        {
            @Override
            public boolean test(String t)
            {
                return true;
            }
        };
        copyAppointProperties(dest, orig, predicate);
    }

    /**
     * 复制指定属性，将列表参数中的属性从orig对象复制到dest<br>
     * 如果两个对象的字段名字相同，但是一个为String，另一个为LocalDate, 会进行转换
     * 
     * 
     * @author 刘湘湘
     * @version 1.1.0<br>
     *          创建时间：2018年9月4日 下午2:49:01
     * @since 1.1.0
     * @param dest
     *            目标对象
     * @param orig
     *            源对象
     * @param fieldList
     *            需要被复制的字段列表（源对象中字段名称包含在列表中才会被复制）
     */
    public static void copyAppointProperties(final Object dest, final Object orig,
            String... fieldList)
    {
        Predicate<String> predicate = new Predicate<String>()
        {
            @Override
            public boolean test(String t)
            {
                return Arrays.stream(fieldList).anyMatch(s -> Objects.equals(s, t));
            }
        };
        copyAppointProperties(dest, orig, predicate);
    }

    /**
     * 复制属性并且忽略掉列表中的属性，将不在列表参数中的属性从orig对象复制到dest<br>
     * 如果两个对象的字段名字相同，但是一个为String，另一个为LocalDate, 会进行转换
     * 
     * @author 刘湘湘
     * @version 1.1.0<br>
     *          创建时间：2018年9月4日 下午2:49:01
     * @since 1.1.0
     * @param dest
     *            目标对象
     * @param orig
     *            源对象
     * @param ignoreFieldList
     *            忽略字段列表（源对象中字段名称包含在列表中才会被复制）
     */
    public static void copyIgnoreProperties(final Object dest, final Object orig,
            String... ignoreFieldList)
    {
        Predicate<String> predicate = new Predicate<String>()
        {
            @Override
            public boolean test(String t)
            {
                return !Arrays.stream(ignoreFieldList).anyMatch(s -> Objects.equals(s, t));
            }
        };
        copyAppointProperties(dest, orig, predicate);
    }

    private static void copyAppointProperties(final Object dest, final Object orig,
            Predicate<String> predicate)
    {
        final PropertyDescriptor[] origDescriptors = beanUtilsBean.getPropertyUtils()
                .getPropertyDescriptors(orig);
        for (PropertyDescriptor origDescriptor : origDescriptors)
        {
            final String name = origDescriptor.getName();
            final Class<?> propType = origDescriptor.getPropertyType();
            if ("class".equals(name))
            {
                continue; // No point in trying to set an object's class
            }
            if (!predicate.test(name))
            {
                continue;
            }
            if (beanUtilsBean.getPropertyUtils().isReadable(orig, name) && beanUtilsBean
                    .getPropertyUtils().isWriteable(dest, name))
            {
                try
                {
                    final Object value = beanUtilsBean.getPropertyUtils().getSimpleProperty(orig,
                            name);
                    if (Objects.equals(LocalDate.class, propType) && Objects.isNull(value))
                    {
                        beanUtilsBean.copyProperty(dest, name, "");
                    } else
                    {
                        beanUtilsBean.copyProperty(dest, name, value);
                    }
                } catch (final NoSuchMethodException
                        | IllegalAccessException
                        | InvocationTargetException e)
                {
                    // Should not happen
                }
            }
        }
    }
}
