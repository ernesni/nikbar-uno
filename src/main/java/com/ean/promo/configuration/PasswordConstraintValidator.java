package com.ean.promo.configuration;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import com.google.common.base.Joiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword arg0) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(

				// longitud entre 8 y 16 caracteres
				new LengthRule(8, 16),
				// al menos dos carcteres en mayúsculas
				new CharacterRule(EnglishCharacterData.UpperCase, 2),
				// al menos dos carcteres en minúsculas
				new CharacterRule(EnglishCharacterData.LowerCase, 2),
				// al menos dos digitos
				new CharacterRule(EnglishCharacterData.Digit, 2),
				// al menos dos símbolos (caracteres especiales)
				new CharacterRule(EnglishCharacterData.Special, 2),

				// definir algunas secuencias ilegales que fallarán cuando >= 5 caracteres de
				// largo
				// alfabético tiene la forma 'abcde', numérico es '34567', qwery es 'asdfg'
				// el parámetro falso indica que se permiten secuencias envueltas; p.ej.
				// 'xyzabc'
				
				// Que no se permita una secuencia de 3 caracteres mayusculas y minusculas
				// juntos
				new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
				// Que no se permita una secuencia de 3 numeros juntos
				new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
				// Regla para determinar si una contraseña contiene una secuencia de teclado
				// QWERTY.
				// Se comprueban las secuencias en mayúsculas y minúsculas.
				new IllegalSequenceRule(EnglishSequenceData.USQwerty, 3, false),

				// No se permiten espacios en blanco
				new WhitespaceRule()));

		RuleResult result = validator.validate(new PasswordData(password));
		if (result.isValid()) {
			return true;
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result)))
				.addConstraintViolation();
		return false;
	}

}