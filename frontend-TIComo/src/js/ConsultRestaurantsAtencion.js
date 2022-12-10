import * as React from 'react';
import { Component } from 'react';
import Table from 'react-bootstrap/Table';
import { IMAGES, ROUTES } from '../components/constants';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLeftLong, faStar } from '@fortawesome/free-solid-svg-icons';
import Tooltip from '@mui/material/Tooltip';

/* CLASS */
class ConsultRestaurantsAtencion extends Component {

	constructor(props) {
		super(props);
	}

	state = {
		restaurants: [], client: {},
		disabled: true
	}

	/* INITIALIZER */
	async componentDidMount() {
		await fetch(ROUTES.PROXY + '/restaurant/showAllRestaurants', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        }).then(data => {
            this.setState({ restaurants: data })
        }).catch((err) => {
            console.log(err);
        })

		await fetch(ROUTES.PROXY + '/client/showClient/' + window.location.href.substring(window.location.href.lastIndexOf('/') + 1), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        }).then(data => {
            this.setState({ client: data })
        }).catch((err) => {
            console.log(err);
        })
	}

	roundAverage(number) {
        let numberRegexp = new RegExp('\\d\\.(\\d){' + 2 + ',}'); 
        if (numberRegexp.test(number)) {   
            return Number(number.toFixed(2));
        } else {
            return Number(number.toFixed(2)) === 0 ? 0 : number; 
        }
    }

	rateColour(rate){
        if (rate>=0 && rate <1){
            return "#f17a45"
        }else if(rate>=1 && rate <2){
            return "#f19745"
        }else if(rate>=2 && rate <3){
            return "#f1a545"
        }else if(rate>=3 && rate <4){
            return "#f1b345"
        }else{
            return "#f1d045"
        }
    }

	/* TABLES */
    RestaurantsTable = (data) => {
        /* Ordenar por valoraciones de mayor a menor */
        data.sort((a, b) => {
            let dateA = a.averageRate;
            let dateB = b.averageRate;

            if (dateA > dateB) {
                return -1;
            }

            if (dateA < dateB) {
                return 1;
            }
        });
        const tableRows = data.map(
            (element) => {
                return (
                    <tr>
                        <td>
                            <button
                                className="button-custom"
                                onClick={() => this.goToRestaurant(element.id)}
                            >
                                {element.name}
                            </button>
                        </td>
                        <td>{element.category}</td>
                        <td>
                            {this.roundAverage(element.averageRate)}<FontAwesomeIcon icon={faStar} font-size={20} color={this.rateColour(element.averageRate)} />
                        </td>
                    </tr>
                )
            }
        )
        return (
            <div style={{ width: "500px" }}>
                <Table striped bordered hover variant="light">
                    <thead>
                        <tr>
                            <th>Restaurante</th>
                            <th>Descripción</th>
                            <th>Valoración</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tableRows}
                    </tbody>
                </Table>
            </div>
        );
    }

	/* EVENTS */
	back = () => {
		this.props.history.push({
			pathname: '/atencion/showAllOrdersByClient/' + window.location.href.substring(window.location.href.lastIndexOf('/') + 1),
		});
	}

	goToRestaurant(restaurantID) {
        this.props.history.push({
            pathname: '/atencion/consultRestaurant',
            client: this.state.client,
            restaurantID: restaurantID,
        });
    }

	render() {
		return (
			<div class="center">
				<img src={IMAGES.LOGO} className="logo" width="150" height="80" alt="" />
				<div class="center">
					<div class="center">
						<div class="card">
							<div>
								<h2 className="text-center mb-4">¿QUÉ QUIERES COMER HOY?</h2>
								<div className="subheading mb-5">
									{this.RestaurantsTable(this.state.restaurants)}
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

export default ConsultRestaurantsAtencion;