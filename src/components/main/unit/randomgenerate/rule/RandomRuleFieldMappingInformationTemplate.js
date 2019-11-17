import React, {Component} from 'react';
import styles from './RandomRuleFieldMappingInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomRuleFieldMappingInformationTemplate extends Component{
    constructor(props) {
        super(props);
    
    }

    render(){
        return(
            <div className="random-rule-field-mapping-information-template">
                    <fieldset className={cx('important-inputs')}>
                        <legend>기본 정보</legend>
                        <table className="input-table">
                            <tr>
                                <td className="title"><b>서비스</b></td>
                                <td className="inputs"><input type="text" name="service" placeholder={this.props.fileName}/></td>
                                <td className="title"><b>메소드</b></td>
                                <td className="inputs"><input type="text" name="method" placeholder={this.props.methodName}/></td>
                            </tr>
                            <tr>
                                <td className="title">레벨</td>
                                <td className="inputs">
                                    <select className="levels" defaultValue={this.props.level}>
                                        <option value={1}>1</option>
                                        <option value={2}>2</option>
                                        <option value={3}>3</option>
                                    </select>
                                </td>
                                <td className="title"><b>랜덤 규칙</b></td>
                                <td className="inputs"><input type="text" name="ruleName" placeholder={this.props.ruleName}/></td>
                            </tr>
                        </table>  
                    </fieldset>
            </div>
        )
    }
}
export default RandomRuleFieldMappingInformationTemplate;