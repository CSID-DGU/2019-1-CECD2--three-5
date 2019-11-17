import React, {Component} from 'react';
import styles from './PairwiseTypeParametersInformationTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseTypeParametersInformationTemplate extends Component{
    render(){
        return(
            <div className="pairwise-type-parameters-information-template">
                    <fieldset className="information" defaultValue= {this.props.value}>
                        <textarea>{this.props.value}</textarea>
                    </fieldset>
            </div>
        )
    }
}
export default PairwiseTypeParametersInformationTemplate;