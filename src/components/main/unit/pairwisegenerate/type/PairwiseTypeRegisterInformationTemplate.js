import React, { Component } from "react";
import styles from "./PairwiseTypeRegisterInformationTemplate.scss";
import Button from 'react-bootstrap/Button';
import classNames from "classnames/bind";
const cx = classNames.bind(styles);

class PairwiseTypeRegisterInformationTemplate extends Component {

  constructor(props) {
    super(props);
  }
  render() {
    const fileName=this.props.fileName;
    const methodName=this.props.methodName;

    return (
      <div className="pairwise-type-register-information-template">
        <fieldset className={cx('important-inputs')}>
        <legend> 기본 정보 </legend>
        <table className="input-table">
            <tr>
              <td className={cx("title")}>서비스</td>
              <td className={cx("inputs")}>
              <input type="text" name="generateNo" placeholder={fileName} />
              </td>
              <td className={cx("title")}>메소드</td>
              <td className={cx("inputs")}>
                <input type="text" name="generateNo" placeholder={methodName} />
              </td><td></td>
            </tr>
          </table>
          </fieldset>
        </div>
    );
  }
}
export default PairwiseTypeRegisterInformationTemplate;
