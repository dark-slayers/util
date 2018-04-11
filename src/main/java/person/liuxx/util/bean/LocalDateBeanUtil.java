package person.liuxx.util.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午3:52:23
 * @since 1.0.0
 */
public final class LocalDateBeanUtil
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
                        .filter(t -> Objects.equals(t, LocalDate.class))
                        .map(t -> cast(t, value))
                        .orElse(null);
            }

            private <T> T cast(Class<T> type, Object value)
            {
                try
                {
                    return type.cast(LocalDate.parse(value.toString()));
                } catch (Exception e)
                {
                    return null;
                }
            }
        }, LocalDate.class);
    }

    private LocalDateBeanUtil()
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
