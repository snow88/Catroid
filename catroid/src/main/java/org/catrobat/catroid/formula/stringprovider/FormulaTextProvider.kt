/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.formula.stringprovider

import android.content.res.Resources
import org.catrobat.catroid.R
import org.catrobat.catroid.formula.Token
import org.catrobat.catroid.formula.function.BinaryFunctionToken
import org.catrobat.catroid.formula.function.FunctionToken
import org.catrobat.catroid.formula.function.UnaryFunctionToken
import org.catrobat.catroid.formula.value.ValueToken

class FormulaTextProvider(val resources: Resources) {

    fun getText(tokens: List<Token>): String {

        var string = ""

        for (token in tokens) {
            string += getText(token) + " "
        }

        return string.trim()
    }

    private fun getText(token: Token) = when (token.type) {
            Token.Type.VALUE -> (token as ValueToken).getString()

            Token.Type.FUNCTION -> getFunctionText(token as FunctionToken)

            else -> resources.getString(token.getResourceId())
    }

    private fun getFunctionText(functionToken: FunctionToken): String {

        var string = resources.getString(functionToken.getResourceId()) +
                resources.getString(R.string.formula_editor_bracket_open)

        if (functionToken is UnaryFunctionToken) {
            string += getText(functionToken.tokens)
        }

        if (functionToken is BinaryFunctionToken) {
            string += getText(functionToken.leftTokens) + ", " + getText(functionToken.rightTokens)
        }

        string += resources.getString(R.string.formula_editor_bracket_close)

        return string
    }
}
