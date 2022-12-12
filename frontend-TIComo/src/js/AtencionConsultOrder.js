import React, { Component } from 'react';
import { IMAGES, ROUTES } from '../components/constants';
import Table from 'react-bootstrap/Table';
import Tooltip from '@mui/material/Tooltip';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLeftLong } from '@fortawesome/free-solid-svg-icons'
import { Button } from '@mui/material';
import RemoveCircleTwoToneIcon from '@mui/icons-material/RemoveCircleTwoTone';
import IconButton from '@mui/material/IconButton';
class AtencionConsultOrder extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        order: this.props.location.order,
        restaurant: {},
        cart: [],
        plates: [],
        disabledModificar: true,
    }

    /* INTIALIZER */
    async componentDidMount() {

        if (this.state.order.state == "DELIVERED") {
            this.state.disabledModificar = true;
        }

        await fetch(ROUTES.PROXY + '/restaurant/showAllPlatesFromRestaurant/' + this.state.order.restaurantID, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            if ([400].indexOf(response.status) !== -1) {
                this.props.history.push(ROUTES.ATENCION);
            }
            return response.json();
        }).then(data => {
            this.setState({ plates: data });
        }).catch((err) => {
            console.log(err);
        })

        await fetch(ROUTES.PROXY + '/order/showPlatesByOrder/' + this.state.order.id, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            if ([400].indexOf(response.status) !== -1) {
                this.props.history.push(ROUTES.ATENCION);
            }
            return response.json();
        }).then(data => {
            this.setState({ cart: data });
        }).catch((err) => {
            console.log(err);
        })

        await fetch(ROUTES.PROXY + '/restaurant/showRestaurant/' + this.state.order.restaurantID, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            if ([400].indexOf(response.status) !== -1) {
                this.props.history.push(ROUTES.ATENCION);
            }
            return response.json();
        }).then(data => {
            this.setState({ restaurant: data });
        }).catch((err) => {
            console.log(err);
        })
    }

    deleteOrder = async () => {
        let mensaje = confirm("¿Desea eliminar este pedido?");
        if (mensaje) {
            console.log(this.state.order.id);
            fetch(ROUTES.PROXY + '/order/deleteOrder/' + this.state.order.id, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            }).then((response) => {
                if (response.status == 200) {
                    this.props.history.push({
                        pathname: '/atencion/showAllOrdersByClient/' + this.state.order.clientID,
                    });
                }
                return response.text();
            })
                .then((responseJson) => {
                    responseJson = JSON.parse(responseJson);
                    document.getElementById("errors").textContent = responseJson.error;
                }).catch((err) => {
                    console.log(err);
                })
        }
    }

    /* METHODS */
    findArrayElementById = (array, id) => {
        return array.find((element) => {
            return element.id === id;
        })
    }

    /* TABLES */
    CartTable = (data) => {
        const tableRows = data.map(
            (element) => {
                return (
                    <tr>
                        <td>{(this.findArrayElementById(this.state.plates, Number(element.plateID))).name}</td>
                        <td>{element.quantity}</td>
                        <td>{element.quantity * (this.findArrayElementById(this.state.plates, Number(element.plateID))).cost}</td>
                        <td hidden={this.state.disabledModificar ? true : false}><IconButton color='secondary' aria-label="delete">
                                <RemoveCircleTwoToneIcon />
                            </IconButton>
                        </td>
                    </tr>
                )
            }
        )

        return (
            <Table striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Cantidad</th>
                        <th>Coste</th>
                        <th hidden={this.state.disabledModificar ? true : false}>Editar</th>
                    </tr>
                </thead>
                <tbody>
                    {tableRows}
                </tbody>
            </Table>
        );
    }

    /* EVENTS */
    back = () => {
        this.props.history.push({
            pathname: '/atencion/showAllOrdersByClient/' + this.state.order.clientID,
        })
    }

    goToModify(order) {
        this.props.history.push({
            pathname: '/atencion/modifyOrder',
            order: order
        })
    }

    handleModifyClik() {
		this.setState({ disabledModificar: !this.state.disabledModificar })
	}
    render() {
        return (
            <div class="center">
                <img src={IMAGES.LOGO} className="logo" width="150" height="80" alt="" />
                <div class="center">
                    <div class="center">
                        <div class="card">
                            <div>
                                <h5 class="text-center mb-4">PEDIDO</h5>
                                <div className="subheading mb-5">
                                    {this.CartTable(this.state.cart)}
                                </div>
                                <h5 class="text-center mb-4">PRECIO TOTAL: {this.state.order.price} €</h5>
                            </div>
                            <div class="columns">
                               
                                    <input type="submit" value="Modificar" onClick={this.handleModifyClik.bind(this)} />
                                    <input type="submit" value="Eliminar" onClick={() => this.deleteOrder(this.state.order)} />
                                
                            </div>
                            <div hidden={this.state.disabledModificar ? true : false}>
									<label>¿Desea guardar los cambios?</label>
									<div class="columns">
										<input type="submit" value="ACEPTAR" onClick={() => this.modifyAdmin()} />
										<input type="submit" value="CANCELAR" onClick={() => this.back()} />
									</div>
								</div>
                            <div class="columnsForIcons">
                                <Tooltip title="Cancelar" placement="top-start">
                                    <FontAwesomeIcon icon={faLeftLong} font-size={20} color={"#000000"} onClick={() => this.back()} />
                                </Tooltip>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default AtencionConsultOrder;