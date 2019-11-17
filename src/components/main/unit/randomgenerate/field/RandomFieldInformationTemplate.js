/**
 * 작성자: 김지운, 원예인
 * 설명: 필드유형 등록 탭 상단의 레벨 정보 테이블
 */
import React, {Component} from 'react';
import styles from './RandomFieldInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomFieldInformationTemplate extends Component{
    render(){
        return(
            <div className="random-field-information-template">
                    <fieldset className="information">
                        <legend>레벨 정보 </legend>
                        <table className="information-table">
                            <tr>
                                <td className="title">level 1: </td>
                                <td>숫자 또는 문자열</td>
                            </tr>
                            <tr>
                                <td className="title">level 2: </td>
                                <td>범위가 정해진 숫자 또는 형식이 있는 문자열</td>
                            </tr>
                            <tr>
                                <td className="title">level 3: </td>
                                <td>특정한 조건이 주어진 문자열</td>
                            </tr>
                        </table>  
                    </fieldset>
            </div>
        )
    }
}
export default RandomFieldInformationTemplate;