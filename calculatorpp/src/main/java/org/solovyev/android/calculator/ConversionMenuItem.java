package org.solovyev.android.calculator;

import android.content.Context;
import jscl.NumeralBase;
import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;
import org.solovyev.android.calculator.jscl.JsclOperation;
import org.solovyev.android.menu.AMenuItem;

/**
 * User: serso
 * Date: 9/21/12
 * Time: 12:11 AM
 */
enum ConversionMenuItem implements AMenuItem<CalculatorDisplayViewState> {

    convert_to_bin(NumeralBase.bin),
    convert_to_dec(NumeralBase.dec),
    convert_to_hex(NumeralBase.hex);

    @NotNull
    private final NumeralBase toNumeralBase;

    ConversionMenuItem(@NotNull NumeralBase toNumeralBase) {
        this.toNumeralBase = toNumeralBase;
    }

    protected boolean isItemVisibleFor(@NotNull Generic generic, @NotNull JsclOperation operation) {
        boolean result = false;

        if (operation == JsclOperation.numeric) {
            if (generic.getConstants().isEmpty()) {
                convert(generic);

                // conversion possible => return true
                result = true;
            }
        }

        return result;
    }

    @Override
    public void onClick(@NotNull CalculatorDisplayViewState data, @NotNull Context context) {
        final NumeralBase fromNumeralBase = CalculatorLocatorImpl.getInstance().getEngine().getNumeralBase();

        final Generic lastResult = data.getResult();

        if (lastResult != null) {
            convert(lastResult);
        }
    }

    private void convert(@NotNull Generic generic) {
        CalculatorLocatorImpl.getInstance().getCalculator().convert(generic, this.toNumeralBase);
    }
}
