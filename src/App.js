import React, { Component } from 'react';
import { Route } from 'react-router-dom';

import HeaderTemplate from 'components/common/HeaderTemplate';
import DashboardTemplate from 'components/common/dashboard/DashboardTemplate';
import FooterTemplate from 'components/common/FooterTemplate';
import LoginTemplate from 'components/login//LoginTemplate';
import ServiceRunTemplate from 'components/main/unit/servicerun/ServiceRunTemplate';
import RandomGenerationTemplate from 'components/main/unit/randomgenerate/generation/RandomGenerationTemplate';
import RandomAlgorithmTemplate from 'components/main/unit/randomgenerate/algorithm/RandomAlgorithmTemplate';
import RandomRuleTemplate from 'components/main/unit/randomgenerate/rule/RandomRuleTemplate';
import RandomFieldTemplate from 'components/main/unit/randomgenerate/field/RandomFieldTemplate';
import FilesCollectionTemplate from 'components/main/unit/filescollection/FilesCollectionTemplate';
import StructsCollectionTemplate from 'components/main/unit/structscollection/StructsCollectionTemplate';

import FindUserIdTemplate from 'components/login/Modal/FindUserIdTemplate';
import ReissuanceUserPasswordTemplate from 'components/login/Modal/ReissuanceUserPasswordTemplate';
import SaveUserRequestTemplate from 'components/login/Modal/SaveUserRequestTemplate';

import AlignColumnTemplate from 'components/common/aligncolumn/AlignColumnTemplate'

import Header2 from 'components/common/Header2';
import Header3 from 'components/common/Header3';
import UnitMainBodyTemplate from 'components/main/unit/UnitMainBodyTemplate'; //KOS20190807


import * as Util from './lib/Util';
import './App';

class App extends Component {
  constructor(props){
    super(props);

    this.state = {
      show: true
    }
  }
  render() {
    return (
      <div className="app"> {/*Roter는 Element를 한개만 가짐*/}
        {/* 로그인 경로 */}
        <Route exact path="/" component={LoginTemplate}/>
        <Route path="/login" component={LoginTemplate}/>

        {/*
        <Route path="/login/FindUserId" component={() => <FindUserIdTemplate show={true} />} />
        <Route path={"/login/ReissuanceUserPassword"} component={() => <ReissuanceUserPasswordTemplate show={true} />} />
        <Route path={"/login/SaveUserRequest"} component={() => <SaveUserRequestTemplate show={true} />} />
        */}

        {/* 메인 페이지 경로 */}
        <Route path="/MainPage" component={Header3}/>


        <Route path="/MainPage/Dashboard"   component={DashboardTemplate}/>

        {/*
        <Route path={Util.URL_RANDOM_GENERATION} component={RandomGenerationTemplate}/>
        <Route path={Util.URL_RANDOM_ALGORITHM} component={RandomAlgorithmTemplate}/>
        <Route path={Util.URL_RANDOM_RULE} component={RandomRuleTemplate}/>
        <Route path={Util.URL_RANDOM_FIELD} component={RandomFieldTemplate}/>
        <Route path={Util.URL_SERVICE_RUN} component={ServiceRunTemplate}/>
        <Route path={Util.URL_FILES_COLLECT} component={FilesCollectionTemplate}/>
        <Route path={Util.URL_STRUCTS_COLLECT} component={StructsCollectionTemplate}/>
        <Route path={Util.URL_TESTCASE_EDIT} component={EditTCTemplate}/>
        */}
        <Route path="/MainPage/UnitMainBody"       component={UnitMainBodyTemplate}/>

        <Route path="/MainPage" component={FooterTemplate}/>
        <Route path="/Main" component={FooterTemplate}/>
      </div>
    );
  }
}

export default App;
