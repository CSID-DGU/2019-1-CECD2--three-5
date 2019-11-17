/**
 * 작성자: 김지운, 원예인
 * 설명: 테스트케이스 목록반영 탭 상단의 기본정보 테이블
 */
import React, {Component} from 'react';
import styles from './PairwiseGenerationResultInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseGenerationResultInformationTemplate extends Component{
    constructor(props) {
        super(props);

        this.state = {
            fileName: this.props.fileName,
            methodName: this.props.methodName,
            ruleName: this.props.ruleName,
            generatedCount: this.props.generatedCount
        };
    }

    render(){
        return(
            <div className="pairwise-generation-result-information-template">
                    <fieldset className={cx('information')}>
                        <legend>기본 정보</legend>
                        <table className="information-table">
                            <tr>
                                <td className="title"><b>서비스</b></td>
                                <td className="inputs"><input type="text" name="service" placeholder={this.state.fileName}/></td>
                                <td className="title"><b>메소드</b></td>
                                <td className="inputs"><input type="text" name="method" placeholder={this.state.methodName}/></td>
                                <td className="title"><b>테스트케이스 개수</b></td>
                                <td className="inputs"><input type="text" name="testcaseNo" placeholder={this.state.generatedCount}/></td>
                            </tr>
                        </table>
                    </fieldset>
            </div>
        )
    }
}
export default PairwiseGenerationResultInformationTemplate;
