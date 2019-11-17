import React, {Component} from 'react';
import styles from './RandomRuleParametersInputTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomRuleParametersInputTemplate extends Component{
    render(){
        return(
            <div className="random-rule-parameters-input-template">
                    <fieldset className="information">
                        <legend></legend>
                        <table className="information-table">
                            <tr>
                                <td className="title">입력 파라미터</td>
                                <td className="inputs"><input type="text" name="fieldtype" placeholder="N 0, min -2147483648, max 2147483647, isNormal true"/></td>
                            </tr>
                        </table>  
                    </fieldset>
            </div>
        )
    }
}
export default RandomRuleParametersInputTemplate;