import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import Header from 'components/common/Header';

class HeaderTemplate extends Component {

  constructor(props,context){
    super(props, context);

    this.handleClickPulldown = this.handleClickPulldown.bind(this);
    this.handleClickMyInfo = this.handleClickMyInfo.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.handleShow = this.handleShow.bind(this);
    this.handleClose = this.handleClose.bind(this);

    this.state = {
      showPulldown: false,
      showMyInfo: false,
    };
  }

  //PudownMenu 조회
  pulldown = {
    submenu:[
      {
        title: "단위 테스트",
        submenu: [
          {
            title: "수집기",
            submenu: [
              {
                title: "정보 수집",
                submenu: [
                  {
                    title: "소스 수집",
                    url: "/main/unit/body/collector/sourcecode"
                  },
                  {
                    title: "구조 정보 수집",
                    url: "/main/unit/body/collector/metainfo"
                  }
                ]
              },
              {
                title: "로그 수집",
                submenu: [
                  {
                    title: "전문 로그 수집",
                    url: "/main/unit/body/collector/messagelog"
                  },
                  {
                    title: "SQL 로그 수집",
                    url: "/main/unit/body/collector/sqllog"
                  }
                ]
              }
            ]
          },
          {
            title: "생성기",
            submenu: [
              {
                title: "자동 생성",
                submenu: [
                  {
                    title: "자동 생성하기",
                    url: "/main/unit/body/generator/autotcgen"
                  },
                ]
              },
              {
                title: "조합 생성",
                submenu: [
                  {
                    title: "조합 값 관리",
                    url: "/main/unit/body/generator/pairwisevaluemng"
                  },
                  {
                    title: "조합 생성하기",
                    url: "/main/unit/body/generator/pairwisetcgen"
                  }
                ]
              },
              {
                title: "랜덤 생성",
                submenu: [
                  {
                    title: "랜덤 규칙 관리",
                    url: "/main/unit/body/generator/randomrulemng"
                  },
                  {
                    title: "랜덤 생성하기",
                    url: "/main/unit/body/generator/randomtcgen"
                  }
                ]
              }
            ]
          },
          {
            title: "실행기",
            submenu: [
              {
                title: "서비스 실행",
                url: "/main/unit/body/runner/service"
              },
            ]
          },
          {
            title: "통계/리포트",
            submenu: [
              {
                title: "통계",
                submenu: [
                  {
                    title: "업무별",
                    url: "/main/unit/body/summary/business"
                  },
                  {
                    title: "팀별",
                    url: "/main/unit/body/summary/team"
                  },
                  {
                    title: "프로그램별",
                    url: "/main/unit/body/summary/program"
                  },
                ]
              },
              {
                title: "리포트",
                url: "/main/unit/body/reportprint"
              },
            ]
          },
        ]
      },

    ]
  }

  //최근 실행 기능 조회 (Cookie or Storage??)
  latelyRun = {
    submenu: [
      {
        title: "수집기",
        submenu: [
          {
            title: "소스 프로그램 수집",
            condition: "P0001>폴더1",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/collector/sorucecode"
          },
          {
            title: "구조 정보 수집",
            condition: "P0001>폴더1>파일1",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/collector/metainfo"
          }
        ]
      },
      {
        title: "생성기",
        submenu: [
          {
            title: "자동 생성",
            condition: "P0001>폴더1",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/generator/autotcgen"
          },
          {
            title: "랜덤 생성",
            condition: "P0001>폴더1>파일1>메소드1",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/generator/randomtcgen"
          }
        ]
      },
      {
        title: "실행기",
        submenu: [
          {
            title: "서비스 실행",
            condition: "P0001",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/runner/service"
          },
          {
            title: "서비스 실행",
            condition: "P0001>폴더1",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/runner/service"
          }
        ]
      },
      {
        title: "결과조회",
        submenu: [
          {
            title: "실행 순번",
            condition: "10000001",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/runner/history"
          },
        ]
      },
      {
        title: "통계조회",
        submenu: [
          {
            title: "통계>팀별",
            desc: "조건절",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/summary/team"
          },
          {
            title: "리포트",
            desc: "조건절",
            dateTime: "YYYYMMDD hhmmdd",
            url: "/main/unit/body/reportprint"
          }
        ]
      },
    ]
  }

  handleClickPulldown(e) {
    console.log("handleClickPulldown::before::this.state.show==>"+this.state.showPulldown);
    if(this.state.showPulldown == true) {
      this.setState({ showPulldown: false});
    } else {
      this.setState({ showPulldown: true, showMyInfo:false});
    }
    console.log("handleClickPulldown::after::this.state.showPulldown==>"+this.state.showPulldown);
    console.log("handleClickPulldown::click click!!!!!");
    console.log(e.target);
  }

  handleClickMyInfo(e) {
    console.log("handleClickMyInfo::before::this.state.showMyInfo==>"+this.state.showMyInfo);
    if(this.state.showMyInfo == true) {
      this.setState({ showMyInfo: false});
    } else {
      this.setState({ showMyInfo: true, showPulldown:false});
    }
    console.log("handleClickMyInfo::after::this.state.showMyInfo==>"+this.state.showMyInfo);
    console.log("handleClickMyInfo::click click!!!!!");
    console.log(e.target);
  }

  handleClick(e) {
    console.log("before::this.state.show==>"+this.state.show);
  const show = this.state.show==true? false: true;
    this.setState({ show: show});
    console.log("after::this.state.show==>"+this.state.show);
    console.log("click click!!!!!");
    console.log(e.target);
  }

  handleClose(e) {
    this.setState({ show: false});
    console.log("click close!!!!!");
    console.log(e.target);
  }

  handleShow(e) {
    this.setState({ show: true});
    console.log("click show!!!!!");
    console.log(e);
  }

  render() {
    console.log("HeaderTemplate...");
    console.log(this.pulldown);
    console.log(this.pulldown.submenu);
    console.log(this.pulldown.submenu[0]);
    console.log(this.pulldown.submenu[0][0]);
    console.log(this.pulldown.submenu[0][1]);
    return (
      <div>
        <Header
          showPulldown={this.state.showPulldown}
          showMyInfo={this.state.showMyInfo}
          pulldown={this.pulldown}
          latelyRun={this.latelyRun}
          handleClickPulldown={this.handleClickPulldown}
          handleClickMyInfo={this.handleClickMyInfo}
          handleClick={this.handleClick}
          handleShow={this.handleShow}
          handleClose={this.handleClose}
        />
      </div>
    )
  }
}

export default HeaderTemplate;
