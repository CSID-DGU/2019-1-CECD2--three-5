/**
 * 작성자: 김지운, 원예인
 * 설명: 테스트케이스 목록반영 탭 상단의 기본정보 테이블
 */
import React, {Component} from 'react';
import styles from './GenerationDetailEditInputTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class GenerationDetailEditInputTemplate extends Component{
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
            <div className="generation-detail-edit-input-template">
                    <fieldset className={cx('information')}>
                        <legend>기본 정보</legend>
                        <table className="information-table">
                            <tr>
                                <td className="title"><b>테스트케이스 명</b></td>
                                <td className="inputs"><input type="text" name="service" placeholder={this.state.fileName}/></td>
                                <td className="title"><b>테스트케이스 NO</b></td>
                                <td className="inputs"><input type="text" name="method" placeholder={this.state.methodName}/></td>
                            </tr>
                            <tr>
                                <td className="title"><b>테스트케이스 그룹 명</b></td>
                                <td className="inputs"><input type="text" name="testcaseNo" placeholder={this.state.generatedCount}/></td>
                                <td className="title"><b>테스트케이스 그룹 NO</b></td>
                                <td className="inputs"><input type="text" name="testcaseNo" placeholder={this.state.generatedCount}/></td>
                            </tr>
                        </table>
                    </fieldset>
            </div>
        )
    }
}
export default GenerationDetailEditInputTemplate;
