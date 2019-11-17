import React, { Component, Fragment, useState } from 'react';
import Collapse from 'react-bootstrap/Collapse';
import Button from 'react-bootstrap/Button';
import GenerationDetailEditTable from './GenerationDetailEditTable';
import { faAngleDown } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleUp } from '@fortawesome/free-solid-svg-icons'


import './GenerationDetailEditTab.scss'


const GenerationDetailEditTab = ({ text, tabColor }) => {
    const [show, setShow] = useState(true);
    const [arrow, setArrow] = useState(faAngleDown);

    const handleClick = () => {
        setShow(!show);
        if (show)
            setArrow(faAngleDown);
        else
            setArrow(faAngleUp);
    }
    return (
        <Fragment>

            <Button block size="sm"
                onClick={handleClick}
                aria-controls="example-collapse-text"
                aria-expanded={show}
                className={`${tabColor} mb-1`}
                variant="outline-dark"
            >
                <span className="lead">{text}</span>
                <FontAwesomeIcon icon={arrow} size="2x" pull="right" />
            </Button>

            <Collapse in={show}>
                <div > {/*div here is required for Collapse to work */}
                    <GenerationDetailEditTable />
                </div>
            </Collapse>
        </Fragment>
    );
}

export default GenerationDetailEditTab;