//
// Author: Yves Lafon <ylafon@w3.org>
//
// (c) COPYRIGHT MIT, ERCIM, Keio, Beihang, 2017.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.css.properties.css3;

import org.w3c.css.parser.CssStyle;
import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssIdent;
import org.w3c.css.values.CssTypes;
import org.w3c.css.values.CssValue;
import org.w3c.css.values.CssValueList;

import java.util.ArrayList;

import static org.w3c.css.values.CssOperator.SPACE;

/**
 * @spec ttps://www.w3.org/TR/2019/CR-css-scroll-snap-1-20190319/#propdef-scroll-padding-block
 */
public class CssScrollPaddingBlock extends org.w3c.css.properties.css.CssScrollPaddingBlock {

    private CssScrollPaddingBlockStart _longhand_start;
    private CssScrollPaddingBlockEnd _longhand_end;

    protected static final CssIdent auto = CssIdent.getIdent("auto");


    /**
     * Create a new CssScrollPaddingBlock
     */
    public CssScrollPaddingBlock() {
        value = initial;
        _longhand_end = new CssScrollPaddingBlockEnd();
        _longhand_start = new CssScrollPaddingBlockStart();
    }

    /**
     * Creates a new CssScrollPaddingBlock
     *
     * @param expression The expression for this property
     * @throws org.w3c.css.util.InvalidParamException
     *          Expressions are incorrect
     */
    public CssScrollPaddingBlock(ApplContext ac, CssExpression expression, boolean check)
            throws InvalidParamException {
        setByUser();
        CssValue val = expression.getValue();
        char op = expression.getOperator();

        if (check && expression.getCount() > 2) {
            throw new InvalidParamException("unrecognize", ac);
        }
        ArrayList<CssValue> values = new ArrayList<>();
        _longhand_end = new CssScrollPaddingBlockEnd();
        _longhand_start = new CssScrollPaddingBlockStart();

        switch (val.getType()) {
            case CssTypes.CSS_NUMBER:
                val.getCheckableValue().checkEqualsZero(ac, this);
            case CssTypes.CSS_LENGTH:
            case CssTypes.CSS_PERCENTAGE:
                val.getCheckableValue().checkPositiveness(ac, this);
                values.add(val);
                _longhand_start.value = val;
                break;
            case CssTypes.CSS_IDENT:
                if (inherit.equals(val)) {
                    if (expression.getCount() > 1) {
                        throw new InvalidParamException("value",
                                expression.getValue(),
                                getPropertyName(), ac);
                    }
                    values.add(val);
                    _longhand_start.value = inherit;
                    _longhand_end.value = inherit;
                    break;
                }
                if (auto.equals(val)) {
                    values.add(auto);
                    _longhand_start.value = auto;
                    break;
                }
            default:
                throw new InvalidParamException("value",
                        expression.getValue(),
                        getPropertyName(), ac);
        }
        expression.next();
        if (!expression.end()) {
            if (op != SPACE) {
                throw new InvalidParamException("operator", op,
                        getPropertyName(), ac);
            }
            val = expression.getValue();
            switch (val.getType()) {
                case CssTypes.CSS_NUMBER:
                    val.getCheckableValue().checkEqualsZero(ac, this);
                case CssTypes.CSS_LENGTH:
                case CssTypes.CSS_PERCENTAGE:
                    val.getCheckableValue().checkPositiveness(ac, this);
                    values.add(val);
                    _longhand_end.value = val;
                    break;
                case CssTypes.CSS_IDENT:
                    if (auto.equals(val)) {
                        values.add(auto);
                        _longhand_end.value = auto;
                        break;
                    }
                default:
                    throw new InvalidParamException("value",
                            expression.getValue(),
                            getPropertyName(), ac);
            }
            expression.next();
        }
        value = (values.size() == 1) ? values.get(0) : new CssValueList(values);
    }

    public CssScrollPaddingBlock(ApplContext ac, CssExpression expression)
            throws InvalidParamException {
        this(ac, expression, false);
    }

    /**
     * Add this property to the CssStyle.
     *
     * @param style The CssStyle
     */
    public void addToStyle(ApplContext ac, CssStyle style) {
        super.addToStyle(ac, style);
        _longhand_start.addToStyle(ac, style);
        _longhand_end.addToStyle(ac, style);
    }
}

