package com.mark.sudokuchecker.validation;

import com.mark.sudokuchecker.board.BoardSection;

/**
 * This rule use a "binary switchtable" for duplication and completeness
 * checking.
 * <p>
 * When the value N first occurs then sets the Nth bit to 1, next time returns
 * INVALID status due to duplication. The section is complete if all the bits
 * are set to 1. The rule assumes the values are between 1 and the length of the
 * section's valueArray.
 * 
 * @author mark
 *
 */
public class ValidationRuleBitwise extends ValidationRule {


	/* (non-Javadoc)
	 * @see com.mark.sudokuchecker.validation.ValidationRule#validate(com.mark.sudokuchecker.board.BoardSection)
	 */
	public ValidationStatus validate(final BoardSection section) {
		int s = 0;
		for (int i = 0; i < section.getValueArray().length; i++) {
			int x = (int) Math.pow(2, section.getValueArray()[i] - 1);

			// If duplicated then return false, else set the bit.
			if ((s & x) == x) {
				return ValidationStatus.INVALID;
			} else {
				s |= x;
			}
		}
		if (s == (int) Math.pow(2, section.getValueArray().length) - 1) {
			return ValidationStatus.VALID;
		}
		return ValidationStatus.INVALID;
	}
}
