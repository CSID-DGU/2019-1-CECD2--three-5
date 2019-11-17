import React, { Component } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBell } from '@fortawesome/free-solid-svg-icons'
import { faDownload } from '@fortawesome/free-solid-svg-icons'
import { faBars } from '@fortawesome/free-solid-svg-icons'

import NotificationBadge from 'react-notification-badge';
import { Effect } from 'react-notification-badge';


import 'bootstrap/dist/css/bootstrap.min.css';

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Dropdown from 'react-bootstrap/Dropdown';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Button from 'react-bootstrap/Button';
import DropdownButton from 'react-bootstrap/DropdownButton';
import * as Util from '../../lib/Util';
// configuration setup in order to use Header2
////////////////////////////////////////////////////////////////////////
// App.js 에서 
// comment out:
// <Route path="/MainPage" component={HeaderTemplate}/>
// Swap with:
//        <Route path="/MainPage" component={Header2}/>
////////////////////////////////////////////////////////////////////////
// MsgBoxTemplate.js에서
// comment out 
//import { PulldownMenu } from 'components/common/dropdown';

class Header3 extends Component {

  handleRedirect = (e, data) => {
    this.props.history.push(data.url + "?id="+13203+"&projectNo="+45);
  }
    render() {
        console.log(this.props);

        const { handleRedirect} = this;
        return (
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                <Navbar.Brand href="#home">
                    <img
                        src="./../../../../../sparrow_logo.png"
                        width="30"
                        className="d-inline-block align-top"
                        alt="Sparrow logo"
                    />
                    Sothree Testcase Generator
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href={Util.URL_STRUCTS_COLLECT}>수집기</Nav.Link>
                        <NavDropdown title="랜덤 생성기" id="collasible-nav-dropdown">
                            <NavDropdown.Item href={Util.URL_RANDOM_GENERATION+"?id="+13203+"&projectNo="+45}>랜덤 생성 관리</NavDropdown.Item>
                            <NavDropdown.Item href={Util.URL_RANDOM_RULE+"?id="+13203+"&projectNo="+45}>랜덤 규칙 등록</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href={Util.URL_RANDOM_FIELD+"?id="+13203+"&projectNo="+45}>필드 유형 등록</NavDropdown.Item>
                            <NavDropdown.Item href={Util.URL_RANDOM_ALGORITHM+"?id="+13203+"&projectNo="+45}>알고리즘 등록</NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="조합 생성기" id="collasible-nav-dropdown">
                            <NavDropdown.Item href={Util.URL_PAIRWISE_GENERATION+"?id="+13203+"&projectNo="+45}>조합 생성 관리</NavDropdown.Item>
                            <NavDropdown.Item href={Util.URL_PAIRWISE_TYPE+"?id="+13203+"&projectNo="+45}>조합값 등록</NavDropdown.Item>
                        </NavDropdown>     
                         <Nav.Link href={Util.URL_SERVICE_RUN}>실행기</Nav.Link>

                        <Nav.Link href="#deets">도움말</Nav.Link>

                    </Nav>
                    <Nav>
                        <ButtonGroup aria-label="icon groups">
                            <Button variant="dark">
                                <NotificationBadge count={1} effect={Effect.SCALE} className="" />
                                <FontAwesomeIcon icon={faBell} size="2x" />
                            </Button>
                            <Button variant="dark">
                                <FontAwesomeIcon icon={faDownload} size="2x" />
                            </Button>
                        </ButtonGroup>


                        <NavDropdown title="권오승(관리자)" id="collasible-nav-dropdown">
                            <NavDropdown.Item href="#action/3.1">내 계정</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.2">비밀번호 변경</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.3">개인화</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action/3.4">로그아웃</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default Header3;