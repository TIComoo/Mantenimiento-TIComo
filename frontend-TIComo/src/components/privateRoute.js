import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { ROUTES } from './constants';
import { authenticationService } from '../services/authentication-service';

export const PrivateRoute = ({ component: Component, roles, ...rest }) => (
    <Route {...rest} render={props => {
        const currentUser = authenticationService.currentUserValue;
        if (!currentUser) {
            return <Redirect to={{ pathname: '/accessDenied', state: { from: props.location } }} />
        }

        console.log(currentUser.role);
        console.log(roles);

        if (roles && roles.indexOf(currentUser.role) === -1) {
            return <Redirect to={{ pathname: '/accessDenied'}} />
        }

        return <Component {...props} />
    }} />
)