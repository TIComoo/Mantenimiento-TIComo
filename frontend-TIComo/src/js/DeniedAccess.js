import React, { Component } from 'react';
import error from '../.images/cerrar.png'
class DeniedAccess extends Component {
    
    render(){
        return (
            <div class="center">
            <div class="center">
                <div class="center">
                    <div>
                   

                        <div class="card">
                        <img src={error} className= 'logo' width="150" height="150" alt=""/>
                            <h5 class="text-center mb-4">Acceso denegado, vuelva a la ventana anterior.</h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>	
    )}
}
export default DeniedAccess;