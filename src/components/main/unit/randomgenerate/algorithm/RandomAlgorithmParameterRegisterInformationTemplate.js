import React, {Component} from 'react';
import styles from './RandomAlgorithmParameterRegisterInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomAlgorithmParameterRegisterInformationTemplate extends Component{
    constructor(props) {
        super(props);
    }
    render(){
        return(
            <div className="random-algorithm-parameter-register-information-template">
                    <fieldset className={cx('information')}>
                        <legend>기본 정보</legend>
                        <table className="information-table">
                            <tr>
                                <td className="title"><b>알고리즘</b></td>
                                <td className="inputs"><input type="text" name="algorithm" placeholder={this.props.algorithmTypeName}/></td>
                                <td className="title"><b>Return Parameter Type</b></td>
                                <td className="inputs">
                                    <select className="returntype" defaultValue={this.props.algorithmReturnType}>
                                        <option value="int">int</option>
                                        <option value = "double">double</option>
                                        <option value ="string">string</option>
                                    </select>
                                </td>
                            </tr>
                        </table>  
                    </fieldset>
            </div>
        )
    }
}
export default RandomAlgorithmParameterRegisterInformationTemplate;