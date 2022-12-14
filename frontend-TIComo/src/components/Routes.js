import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Login from '../js/Login';
import Register from '../js/Register';
import Admin from '../js/AdminPage';
import AddPlate from '../js/AddPlate';
import AddRestaurant from '../js/AddRestaurant';
import ConsultRestaurant from '../js/ConsultRestaurant';
import Welcome from '../js/Welcome';
import AddAdmin from '../js/AddAdmin';
import ConsultAdmin from '../js/ConsultAdmin';
import ConsultPlate from '../js/ConsultPlate';
import ConsultRider from '../js/ConsultRider';
import ConsultClient from '../js/ConsultClient';
import AddRider from '../js/AddRider';
import PageNotFound from '../js/PageNotFound';
import Value from '../js/Value';
import Client from '../js/ClientPage';
import ClientConsultRestaurant from '../js/ClientConsultRestaurant';
import ClientConsultOrder from '../js/ClientConsultOrder';
import AdminConsultOrder from '../js/AdminConsultOrder';
import Rider from '../js/RiderPage';
import RiderConsultOrder from '../js/RiderConsultOrder';
import ClientConsultPlate from '../js/ClientConsultPlate';

import Atencion from '../js/AtencionPage';
import ConsultOrdersClient from '../js/ConsultOrdersClient';
import AtencionConsultOrder from '../js/AtencionConsultOrder';
import ModifyOrder from '../js/ModifyOrder';
import ConsultRestaurantsAtencion from '../js/ConsultRestaurantsAtencion';
import AtencionConsultRestaurant from '../js/AtencionConsultRestaurant';

import { ROUTES, ROLES} from './constants';
import { PrivateRoute } from './privateRoute';
import DeniedAccess from '../js/DeniedAccess';

function Routes() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path={ROUTES.LOGIN} component={Login} />
        <Route exact path={ROUTES.WELCOME} component={Welcome} />
        <Route exact path="/register" component={Register} />
        <Route exact path = "/deniedAccess" component={DeniedAccess} />

        <PrivateRoute exact path="/admin/consultRestaurant/:id/addPlate" component={AddPlate} />
        <PrivateRoute exact path="/admin/addRestaurant" component={AddRestaurant} />
        <PrivateRoute exact path={ROUTES.ADDADMIN} component={AddAdmin} />
        <PrivateRoute exact path="/admin/addRider" component={AddRider} />
        <PrivateRoute exact path="/admin/ConsultOrder" component={AdminConsultOrder} />
        <PrivateRoute exact path="/admin/consultRestaurant/:id" component={ConsultRestaurant} />
        <PrivateRoute exact path="/admin/consultAdmin/:id" component={ConsultAdmin} />
        <PrivateRoute exact path="/admin/consultRestaurant/:id/consultPlate/:id" component={ConsultPlate} />
        <PrivateRoute exact path="/admin/consultRider/:id" component={ConsultRider} />
        <PrivateRoute exact path="/admin/consultClient/:id" component={ConsultClient} />
        <PrivateRoute path="/admin" roles={[ROLES.ADMIN]} component={Admin} />
        
        <PrivateRoute exact path="/client/ConsultPlate" component={ClientConsultPlate} />
        <PrivateRoute exact path="/client/consultRestaurant" component={ClientConsultRestaurant} />
        <PrivateRoute exact path="/client/consultOrder" component={ClientConsultOrder} />
        <PrivateRoute exact path="/client/orderRate" component={Value} />
        <PrivateRoute path="/client" roles={[ROLES.CLIENT]} component={Client} />

        <PrivateRoute exact path="/rider/consultOrder" component={RiderConsultOrder} />
        <PrivateRoute path="/rider" roles={[ROLES.RIDER]} component={Rider} />
        
        <PrivateRoute exact path="/atencion/showAllOrdersByClient/:id" component={ConsultOrdersClient} />
        <PrivateRoute exact path="/atencion/consultOrder" component={AtencionConsultOrder} />
        <PrivateRoute exact path="/atencion/showAllRestaurants/:id" component={ConsultRestaurantsAtencion} />
        <PrivateRoute exact path="/atencion/consultRestaurant" component={AtencionConsultRestaurant} />
        <PrivateRoute exact path="/atencion/modifyOrder" component={ModifyOrder} />
        <PrivateRoute path="/atencion" roles={[ROLES.ATENCION]} component={Atencion} />

        <Route path="*" component={DeniedAccess} />
      </Switch>
    </BrowserRouter>
  );
}

/*
  




*/ 

export default Routes;