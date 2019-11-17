import React, {Component} from 'react';
import styles from './PairwiseTypeFieldMappingInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseTypeFieldMappingInformationTemplate extends Component{
    constructor(props) {
        super(props);
    
    }

    render(){
        return(
            <div className="pairwise-type-field-mapping-information-template">
                    <fieldset className={cx('important-inputs')}>
                        <legend>기본 정보</legend>
                        <table className="input-table">
                            <tr>
                                <td className="title"><b>서비스</b></td>
                                <td className="inputs"><input type="text" name="service" placeholder={this.props.fileName}/></td>
                                <td className="title"><b>메소드</b></td>
                                <td className="inputs"><input type="text" name="method" placeholder={this.props.methodName}/></td>
                                <td className="title"><b>조합 유형 이름</b></td>
                                <td className="inputs"><input type="text" name="ruleName" placeholder={this.props.ruleName}/></td>
                            </tr>
                        </table>  
                    </fieldset>
            </div>
        )
    }
}
export default PairwiseTypeFieldMappingInformationTemplate;