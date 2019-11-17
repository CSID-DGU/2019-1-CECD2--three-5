/**
 * 작성자: 김지운, 원예인
 * 설명: 테스트케이스 목록반영 탭 상단의 기본정보 테이블
 */
import React, {Component} from 'react';
import styles from './RandomGenerationResultInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomGenerationResultInformationTemplate extends Component{
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
            <div className="random-generation-result-information-template">
                    <fieldset className={cx('information')}>
                        <legend>기본 정보</legend>
                        <table className="information-table">
                            <tr>
                                <td className="title"><b>서비스</b></td>
                                <td className="inputs"><input type="text" name="service" placeholder={this.state.fileName}/></td>
                                <td className="title"><b>메소드</b></td>
                                <td className="inputs"><input type="text" name="method" placeholder={this.state.methodName}/></td>
                            </tr>
                            <tr>
                                <td className="title">랜덤 유형</td>
                                <td className="inputs">
                                    <select className="randomtype">
                                      <option>동국대리허설 규칙</option>
                                      <option>최영진 테스트</option>
                                    </select>
                                </td>
                                <td className="title"><b>생성된 개수</b></td>
                                <td className="inputs"><input type="text" name="testcaseNo" placeholder={this.state.generatedCount}/></td>
                            </tr>
                        </table>
                    </fieldset>
            </div>
        )
    }
}
export default RandomGenerationResultInformationTemplate;
