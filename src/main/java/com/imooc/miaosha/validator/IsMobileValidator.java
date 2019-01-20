package com.imooc.miaosha.validator;

import com.imooc.miaosha.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;
	
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {//默认情况 判断是否是手机号
			return ValidatorUtil.isMobile(value);
		}else {//@isMobile(require = false) 注解后边设置了false
			if(StringUtils.isEmpty(value)) {//如果为空 返回通过
				return true;
			}else {
				return ValidatorUtil.isMobile(value);//如果不为空 进行验证
			}
			//也就是说 设置require = false 即这个参数可传可不传  （还没想到设置false的实际用途--
		}
	}

}
