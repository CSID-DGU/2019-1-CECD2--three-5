import React, { Component, Fragment, useState } from 'react';
import Collapse from 'react-bootstrap/Collapse';
import Button from 'react-bootstrap/Button';
import MappingTable from './TypeFieldMappingTable';
import { faAngleDown } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleUp } from '@fortawesome/free-solid-svg-icons'


import './TypeFieldMappingTab.scss'

class TypeFieldMappingTab extends Component{
    constructor(props){
        super(props);
        this.state={
            text: this.props.text,
            tabColor: this.props.tabColor
        };
    }

    render(){
        
        const show = true;
        const setShow = true;
        const arrow = faAngleDown;
        const setArrow = faAngleDown;
        //const [show, setShow] = useState(true);
        //const [arrow, setArrow] = useState(faAngleDown);

        const handleClick = () => {
            setShow(!show);
            if (show)
                setArrow(faAngleDown);
            else
                setArrow(faAngleUp);
        }


        return(
            <Fragment>

            <Button block size="sm"
                onClick={handleClick}
                aria-controls="example-collapse-text"
                aria-expanded={show}
                className={`${this.state.tabColor} mb-1`}
                variant="outline-dark"
            >
                <span className="lead">{this.state.text}</span>
                <FontAwesomeIcon icon={arrow} size="2x" pull="right" />
            </Button>

            <Collapse in={show}>
                <div > {/*div here is required for Collapse to work */}
                    <MappingTable
                    ruleLevel={this.props.ruleLevel}
                    testResultDetail = {this.props.testResultDetail}/>
                </div>
            </Collapse>
        </Fragment>
        )
    }

}
export default TypeFieldMappingTab;