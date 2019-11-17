/**
 * 작성자: 김지운, 원예인
 * 설명: 랜덤 생성 이력화면 탭 부분의 상단 랜덤 생성 정보 테이블
 */
import React, { Component } from "react";
import styles from "./RandomGenerationInputsTemplate.scss";
import Button from 'react-bootstrap/Button';
import classNames from "classnames/bind";
const cx = classNames.bind(styles);

class RandomGenerationInputsTemplate extends Component {
  render() {
    return (
      <div className="random-generation-inputs-template">
        <fieldset className={cx('information')}>
        <legend> 랜덤 생성 </legend>
        <table className="information-table">
            <tr>
              <td className="title">서비스</td>
              <td className="inputs">
                <input type="text" name="fileName" defaultValue="SocketSUTController" placeholder="ddd" />
              </td>
              <td className="title">메소드</td>
              <td className="inputs">
                <input type="text" name="methodName" defaultValue="selectUserList" placeholder="ddd" />
              </td><td></td>
              </tr>
              <tr>
              <td className="title">랜덤 규칙</td>
              <td className="inputs">
                <select className="randomtype">
                  <option >동국대리허설 규칙</option>
                  <option >최영진 테스트</option>
                </select>
              </td>
              <td className="title">생성할 개수</td>
              <td className="inputs">
                <input type="text" name="generateNo"  defaultValue="10" placeholder="ddd" />
              </td>
              <td>
                <div className={cx("button-group")}>
                  <Button className={cx("start-button")} onClick={() => { this.props.testRun(45, 13203, 2, 5); }} >
                    <i className={cx("fa fa-car")} />
                    <span className={cx("start-font")}>랜덤 생성 시작</span>
                  </Button>
                </div>
              </td>
            </tr>
          </table>
          </fieldset>
        </div>
    );
  }
}
export default RandomGenerationInputsTemplate;
