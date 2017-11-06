package com.mark.sudokuchecker.validation;

import java.util.EnumMap;
import java.util.logging.Logger;

import com.mark.sudokuchecker.board.Board;

/**
 * Board validator class.
 * 
 * @author mark
 *
 */
public class Validation {

	private static final Logger LOGGER = Logger.getLogger(Validation.class.getName());

	/**
	 * Validate the <tt>Board</tt> against the <tt>ValidationRule<tt/>
	 * 
	 * @param board the <tt>Board</tt> to be validated
	 * 
	 * @param xclass
	 *            the <tt>ValidationRule<tt/> class which has to be use for validation
	 * @return <tt>ValidationResult</tt> which contains the result of the validation
	 * @throws ValidationException 
	 */
	public ValidationResult validate(Board board, Class<? extends ValidationRule> xclass) throws ValidationException {

		try {
			EnumMap<ValidationStatus, String> resultMap = new EnumMap<>(ValidationStatus.class);
			// Rule instance
			ValidationRule validationRule = xclass.newInstance();

			// Sectionwise validation
			LOGGER.info("Validation starts.");
			board.getSectionList().forEach(section -> {
				String value;
				ValidationStatus key = validationRule.validate(section);
				LOGGER.info("Validation: sectionCode:" + section.getSectionCode() + " result:" + key);
				if (resultMap.containsKey(key)) {
					value = resultMap.get(key) + "," + section.getSectionCode();
				} else {
					value = section.getSectionCode();
				}
				resultMap.put(key, value);
			});
			LOGGER.info("Validation finished.");

			// Extract result
			ValidationResult result;
			if (resultMap.containsKey(ValidationStatus.INVALID)) {
				// Invalid
				String statusText = "The following sections contains error:" + resultMap.get(ValidationStatus.INVALID);
				result = new ValidationResult(ValidationStatus.INVALID, statusText);
			} else {
				// Valid
				String statusText = "No errors. The sudoku is valid.";
				result = new ValidationResult(ValidationStatus.VALID, statusText);
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.severe("Validation error:" + e.getMessage());
			throw new ValidationException(e.getMessage());
		}
	}

}
