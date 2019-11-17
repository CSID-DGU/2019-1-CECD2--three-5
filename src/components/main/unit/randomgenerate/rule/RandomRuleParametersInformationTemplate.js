import React, {Component} from 'react';
import styles from './RandomRuleParametersInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomRuleParametersInformationTemplate extends Component{
    render(){
        return(
            <div className="random-rule-parameters-information-template">
                    <fieldset className="information">
                    <legend>기본 정보</legend>
                        <table className="information-table">
                            <tr>
                                <td className="title"><b>필드 유형</b></td>
                                <td className="inputs"><input type="text" name="fieldtype" placeholder="유형5"/></td>
                                <td className="title"><b>레벨</b></td>
                                <td className="inputs">
                                    <select className="levels">
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                    </select>
                                </td>
                            </tr>
                        </table>  
                    </fieldset>
            </div>
        )
    }
}
export default RandomRuleParametersInformationTemplate;