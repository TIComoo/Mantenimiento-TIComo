import React, { Component } from 'react';
import '../css/StylePage.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ROUTES } from '../components/constants';
import { Rating } from 'react-simple-star-rating';
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import IconButton from "@mui/material/IconButton";
import AddCircleIcon from '@mui/icons-material/AddCircle';
import RemoveCircleIcon from '@mui/icons-material/RemoveCircle';
import Table from 'react-bootstrap/Table';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLeftLong ,faEdit} from '@fortawesome/free-solid-svg-icons'
import Tooltip from '@mui/material/Tooltip';
import { authenticationService } from '../services/authentication-service';

const fillColorArray = [
    "#f17a45",
    "#f19745",
    "#f1a545",
    "#f1b345",
    "#f1d045",
];
class ModifyOrder extends Component {
    constructor(props) {
        super(props);
    }
    
    state = {
        
        restaurantID: this.props.location.order.restaurantID,
        orderID: this.props.location.order.id,
        restaurant: [],
        plates: [],
        disabled: true,
        platesAndOrder:[],
        cart: {},
        rateRestaurant: 0,
        totalPrice: 0,
        client: this.props.location.order.clientID,
        rellenar: true
    }


    /* INTIALIZER */
    async componentDidMount() {

        await fetch(ROUTES.PROXY + '/restaurant/showRestaurant/' + this.state.restaurantID, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            if ([400].indexOf(response.status) !== -1) {
                this.props.history.push(ROUTES.ADMIN);
                authenticationService.logout();
            }
            return response.json();
        }).then(data => {
            this.setState({ restaurant: data })
        }).catch((err) => {
            console.log(err);
        })

        await fetch(ROUTES.PROXY + '/order/orderCart/' + this.state.orderID, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            if ([400].indexOf(response.status) !== -1) {
                this.props.history.push(ROUTES.ADMIN);
                authenticationService.logout();
            }
            return response.json();
        }).then(data => {
            this.setState({ platesAndOrder: data })
        }).catch((err) => {
            console.log(err);
        })

        await fetch(ROUTES.PROXY + '/restaurant/showAllPlatesFromRestaurant/' + this.state.restaurantID, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        }).then(data => {
            this.setState({ plates: data })
        }).catch((err) => {
            console.log(err);
        })

        this.rellenar(this.state.platesAndOrder)
    }

    findArrayElementById = (array, id) => {
        return array.find((element) => {
            return element.id === id;
        })
    }

    /* METHODS */
    rellenar = async (datap)=>{

        for(let i=0;i<datap.length;i++){
            this.shop(datap[i].id,1)
        }

    }
    shop = async (plateID, action) => {
        switch (action) {
            case 1: {
                if (this.state.cart[plateID]) {
                    this.state.cart[plateID] = { "quantity": this.state.cart[plateID].quantity + 1, "price": this.findArrayElementById(this.state.plates, plateID).cost }
                } else {
                    this.state.cart[plateID] = { "quantity": 1, "price": this.findArrayElementById(this.state.plates, plateID).cost };
                }
                console.log(this.state.cart[plateID].quantity)
                this.state.totalPrice += this.state.cart[plateID].price;
                break;
            }
            case 2: {
                this.state.totalPrice -= this.state.cart[plateID].price;
                if (this.state.cart[plateID].quantity > 1) {
                    this.state.cart[plateID] = { "quantity": this.state.cart[plateID].quantity - 1, "price": this.state.cart[plateID].price }
                }else{
                    delete this.state.cart[plateID];
                }
                break;
            }
            default:
        }
        
        this.props.history.push()

    }

    /* TABLES */
    PlatesTable = (data) => {
        return (
            <ImageList sx={{ width: 390, height: 450 }}>
                {data.map((element) => (
                    <ImageListItem key={element.img}>
                        <img
                            src={`${element.photo}?w=248&fit=crop&auto=format`}
                            srcSet={`${element.photo}?w=248&fit=crop&auto=format&dpr=2 2x`}
                            alt={element.name}
                            loading="lazy"
                            onClick={() => this.goToPlate(element)}
                            style={{ cursor: 'pointer' }}
                        />
                        <ImageListItemBar

                            title={element.name}
                            subtitle={element.cost + '€'}
                            actionIcon={
                                <IconButton
                                    sx={{ color: "rgba(255, 255, 255, 0.54)" }}
                                    aria-label={`info about ${element.title}`}
                                >
                                    <AddCircleIcon onClick={() => this.shop(element.id, 1)} />
                                    <RemoveCircleIcon onClick={() => this.shop(element.id, 2)} />
                                </IconButton>
                            }
                        />
                    </ImageListItem>
                ))}
            </ImageList>
        )
    }

    /* TABLES */
    CartTable = (data) => {
        console.log(data);
        const tableRows = Object.entries(data)
            .map(([key, value]) => {
                return (
                    <tr>
                        <td>{(this.findArrayElementById(this.state.plates, Number(key))).name}</td>
                        <td>{value.quantity}</td>
                        <td>{value.quantity * value.price}€</td>
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
                    </tr>
                </thead>
                <tbody>
                    {tableRows}
                </tbody>
            </Table>
        );
    }

    /* EVENTS */
    onClick(e) {
        e.preventDefault();
    }

    back = () => {
        this.props.history.push({
            pathname: '/client'
        })
    }

    handleModifyClik() {
        console.log(this.state)
        this.setState({ disabled: !this.state.disabled })
    }

    handleChange = async e => {
        this.setState({
            restaurant: {
                ...this.state.restaurant,
                [e.target.name]: e.target.value
            }
        });
    }

    goToCart() {
        this.props.history.push({
            pathname: '/makeOrder',
            cart: this.state.cart,
            plates: this.state.plates,
            client: this.state.client,
            restaurantID: this.state.restaurantID,
        });
    }

    goToPlate(plate,) {
        this.props.history.push({
            pathname: '/client/consultPlate',
            plate: plate,
            restaurant: this.state.restaurant
        });
    }


    goToOrders(data){
        console.log(this.state.cart);
        var mensaje = confirm("¿Desea guardar los cambios?");
        if (mensaje) {


            fetch(ROUTES.PROXY +'/order/modifyOrderAtencion/' + data, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    cart: this.state.cart,
                    orderID: this.state.orderID,
                })
            }).then((response) => {
                console.log(response)
                if (response.status == 200) {
                    alert("Cambios guardados correctamente");
                    this.props.history.push({
                        pathname: '/atencion',
                        id: this.state.client,
                        restaurantID: this.state.restaurantID,
                        value: 3
                    });
                }
                return response.text();
            })
            .catch((err) => {
                console.log(err);
            })
        }
        
    }

    //  clearCacheData = () => {
    //     caches.keys().then((names) => {
    //       names.forEach((name) => {
    //         caches.delete(name);
    //       });
    //     });
    //   };

    render() {
        return (
            <div class="row">
                <div className='col-4'>
                    <div class="cardInColumn">
                        <h5 class="text-center mb-4">INFORMACIÓN RESTAURANTE</h5>
                        <label class="form-control-label px-0" >Nombre<span class="text-danger"> *</span></label>
                        <input type="text" name="name" placeholder={this.state.restaurant.name} disabled={(this.state.disabled) ? "disabled" : ""} required="" onChange={this.handleChange} />
                        <label class="form-control-label px-0">Email<span class="text-danger"> *</span></label>
                        <input type="text" name="email" placeholder={this.state.restaurant.email} disabled={(this.state.disabled) ? "disabled" : ""} required="" onChange={this.handleChange} />
                        <label class="form-control-label px-0">CIF<span class="text-danger"> *</span></label>
                        <input type="text" name="cif" placeholder={this.state.restaurant.cif} disabled={(this.state.disabled) ? "disabled" : ""} required="" onChange={this.handleChange} />
                        <label class="form-control-label px-0">Dirección<span class="text-danger"> *</span></label>
                        <input type="text" name="address" placeholder={this.state.restaurant.address} disabled={(this.state.disabled) ? "disabled" : ""} required="" onChange={this.handleChange} />
                        <label class="form-control-label px-0">Teléfono<span class="text-danger"> *</span></label>
                        <input type="text" name="phone" placeholder={this.state.restaurant.phone} disabled={(this.state.disabled) ? "disabled" : ""} required="" onChange={this.handleChange} />
                        <label class="form-control-label px-0">Categoría<span class="text-danger"> *</span></label>
                        <input type="text" name="category" placeholder={this.state.restaurant.category} disabled={(this.state.disabled) ? "disabled" : ""} required="" onChange={this.handleChange} />
                        <label class="form-control-label px-0">Registro Mercantil<span class="text-danger"> *</span></label>
                        <input type="text" name="commercialRegister" placeholder={this.state.restaurant.commercialRegister} disabled={(this.state.disabled) ? "disabled" : ""} required="" onChange={this.handleChange} />
                        <label class="form-control-label px-0"> Rate </label>
                        <Rating
                            onClick={this.rateRestaurant}
                            initialValue={this.state.restaurant.averageRate}
                            size={50}
                            transition
                            fillColorArray={fillColorArray}
                            readonly={true}

                        />
                        <div class="columnsForIcons">
                        <Tooltip title="Cancelar" placement="top-start">
                            <FontAwesomeIcon icon={faLeftLong} font-size={20} color={"#000000"} onClick={() => this.back()} />
                        </Tooltip>
                        </div>
                    </div>
                </div>

                <div className='col-4'>
                    <div class="cardInColumn">
                        <h5 class="text-center mb-4">CARTA DEL RESTAURANTE</h5>
                        <div className="subheading mb-5">
                            {this.PlatesTable(this.state.plates)}
                        </div>
                    </div>
                </div>
                <div className='col-4'>
                    <div class="cardInColumn">
                        <h5 class="text-center mb-4">PEDIDO</h5>
                        <div className="subheading mb-5">
                            {this.CartTable(this.state.cart)}
                        </div>
                        <h5 class="text-center mb-4">PRECIO TOTAL: {this.state.totalPrice} €</h5>
                        <Tooltip title="Modificar pedido" placement="top-start">
                            <FontAwesomeIcon icon={faEdit} font-size={20} color={"#000000"} onClick={() => this.goToOrders(this.state.orderID)} />
                        </Tooltip>
                    </div>                                    
                </div>
            </div>
        )
    }
}
<FontAwesomeIcon icon="fa-solid fa-pen-to-square" />
export default ModifyOrder;