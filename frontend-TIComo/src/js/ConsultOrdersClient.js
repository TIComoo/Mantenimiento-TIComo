import * as React from 'react';
import { Component } from 'react';
import Table from 'react-bootstrap/Table';
import { IMAGES, ROUTES } from '../components/constants';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLeftLong } from '@fortawesome/free-solid-svg-icons';
import Tooltip from '@mui/material/Tooltip';

/* CLASS */
class ConsultOrdersClient extends Component {

	constructor(props) {
		super(props);
		this.onClick = this.onClick.bind(this);
	}

	state = {
		orders: [], restaurants: [],
		disabled: true
	}

	/* INITIALIZER */
	componentDidMount() {
		fetch(ROUTES.PROXY + '/order/showAllOrdersByClient/' + window.location.href.substring(window.location.href.lastIndexOf('/') + 1), {
			method: 'GET',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
		}).then((response) => {
			if ([400].indexOf(response.status) !== -1) {
				this.props.history.push({
					pathname: '/atencion',
					value: 2,
				});
			}
			return response.json();
		}).then(data => {
			this.setState({ orders: data })
			console.log(this.state)
		}).catch((err) => {
			console.log(err);
		})

		fetch(ROUTES.PROXY + '/restaurant/showAllRestaurants', {
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
	}

	findArrayElementById = (array, id) => {
		return array.find((element) => {
			return element.id === id;
		})
	}

	/* TABLES */
	OrdersTable = (data) => {
		/* Ordenar por fecha de realización de más a menos nuevo */
		data.sort((a, b) => {
			let dateA = a.releaseDate.split('T')[0];
			let dateB = b.releaseDate.split('T')[0];

			if (dateA > dateB) {
				return -1;
			}

			if (dateA < dateB) {
				return 1;
			}

			let timeA = a.releaseDate.split('T')[1];
			let timeB = b.releaseDate.split('T')[1];

			if (timeA > timeB) {
				return -1;
			}

			if (timeA < timeB) {
				return 1;
			}
		});
		const tableRows = data.map(
			(element) => {
				return (
					<tr>
						<td>{(this.findArrayElementById(this.state.restaurants, Number(element.restaurantID))).name}</td>
						<td>
							<button
								className="button-custom"
								onClick={() => this.goToOrder(element)}
							>
								{element.releaseDate.split('T')[0]}
							</button>
						</td>
						<td>{element.releaseDate.split('T')[1].substring(0, 8)}</td>
						<td>{element.state}</td>
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
							<th>Dia</th>
							<th>Hora</th>
							<th>Estado</th>
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
	onClick(e) {
		e.preventDefault();
	}

	back = () => {
		this.props.history.push({
			pathname: '/atencion',
			value: 2,
		});
	}

	goToOrder(order) {
		this.props.history.push({
			pathname: '/atencion/consultOrder',
			order: order,
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
								<h2 class="text-center mb-4">PEDIDOS</h2>
								<div className="subheading mb-5">
									{this.OrdersTable(this.state.orders)}
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

export default ConsultOrdersClient;