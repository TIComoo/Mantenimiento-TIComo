import React, { Component } from 'react';
import '../css/StylePage.css';
import PasswordChecklist from "react-password-checklist"
import { IMAGES, ROUTES } from '../components/constants';
import Tooltip from '@mui/material/Tooltip';

class Register extends Component {

	state = {
		form: {
			name: '',
			surname: '',
			nif: '',
			address: '',
			phone: '',
			email: '',
			password: '',
			passwordAgain: '',
			validPassword: true
		}
	}

	/* METHODS */
	register = async () => {
		
		fetch(ROUTES.PROXY + '/user/register', {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				name: this.state.form.name,
				surname: this.state.form.surname,
				nif: this.state.form.nif,
				address: this.state.form.address,
				phone: this.state.form.phone,
				email: this.state.form.email,
				password: this.state.form.password,
				role: "CLIENT"
			})
		}).then((response) => {
			if (response.status == 200) {
				alert("Usuario registrado correctamente");
				this.props.history.push(ROUTES.LOGIN);
			}
			return response.text();
		}).then((responseJson) => {
			responseJson = JSON.parse(responseJson);
			document.getElementById("email").textContent = responseJson.errorEmail;
			document.getElementById("name").textContent = responseJson.errorName;
			document.getElementById("surname").textContent = responseJson.errorSurname;
			document.getElementById("nif").textContent = responseJson.errorNIF;
			document.getElementById("phone").textContent = responseJson.errorPhone;
			document.getElementById("address").textContent = responseJson.errorAddress;
		}).catch((err) => {
			console.log(err);
		})
		
	}

	/* EVENTS */
	back = () => {
		window.location.href = "/";
	}

	handleChange = async e => {
		this.setState({
			form: {
				...this.state.form,
				[e.target.name]: e.target.value
			}
		});
	}

	validPassword = () => {
		if(this.state.form.validPassword){
			this.state.form.validPassword = false
		}else{
			this.state.form.validPassword = true
		}
	}

	render() {
		return (
			<div class="center">
				<div className="center">
					<div className="center">
							<div class="card">
								<img src={IMAGES.LOGO} className="centerImage" width="150" height="80" alt="" />
								<h5 class="text-center mb-4">DATOS PERSONALES</h5>

								<Tooltip title="No puede contener: [1-9]/*@..." placement="left-start">
								<label class="form-control-label px-0">Nombre<span class="text-danger"> *</span></label>
								</Tooltip>
								<input class="text" type="text" name="name" placeholder="Nombre" required="" onChange={this.handleChange} />
								<label class="text-danger" id="name"></label>

								<Tooltip title="No puede contener: [1-9]/*@..." placement="left-start">
								<label class="form-control-label px-0">Apellidos<span class="text-danger"> *</span></label>
								</Tooltip>
								<input class="text" type="text" name="surname" placeholder="Apellidos" required="" onChange={this.handleChange} />
								<label class="text-danger" id="surname"></label>

								<Tooltip title="Debe tener 8 n??meros y 1 letra" placement="left-start">
								<label class="form-control-label px-0">NIF<span class="text-danger"> *</span></label>
								</Tooltip>
								<input class="text" type="text" name="nif" placeholder="000000000X" required="" onChange={this.handleChange} />
								<label class="text-danger" id="nif"></label>

								<label class="form-control-label px-0">Direcci??n<span class="text-danger"> *</span></label>
								<input class="text" type="text" name="address" placeholder="C/" required="" onChange={this.handleChange} />
								<label class="text-danger" id="address"></label>

								<Tooltip title="Debe tener 9 n??meros y existir en Espa??a" placement="left-start">
								<label class="form-control-label px-0">Tel??fono<span class="text-danger"> *</span></label>
								</Tooltip>
								<input class="text" type="text" name="phone" placeholder="666666666" required="" onChange={this.handleChange} />
								<label class="text-danger" id="phone"></label>

								<h5 class="text-center mb-4"></h5>
								<h5 class="text-center mb-4">DATOS PARA EL LOGIN</h5>
								
								<label class="form-control-label px-0">Email<span class="text-danger"> *</span></label>
								<input class="text" type="text" name="email" placeholder="Ejemplo@gmail.com" required="" onChange={this.handleChange} />
								<label class="text-danger" id="email"></label>

								<label class="form-control-label px-0">Contrase??a<span class="text-danger"> *</span></label>
								<input class="text" type="password" name="password" placeholder="****" required="" onChange={this.handleChange} />
								
								<label class="form-control-label px-0">Repetir Contrase??a<span class="text-danger"> *</span></label>
								<input class="text" type="password" name="passwordAgain" placeholder="****" required="" onChange={this.handleChange} />
								
								<PasswordChecklist
									rules={["minLength","lowercase","capital","number","specialChar","match"]}
									minLength={8}
									value={this.state.form.password}
									valueAgain={this.state.form.passwordAgain}
									onChange={this.validPassword}
									messages={{
										minLength: "La contrase??a tiene m??s de 8 caracteres.",
										lowercase: "La contrase??a tiene una letra min??scula.",
										capital: "La contrase??a tiene una letra may??scula.",
										number: "La contrase??a tiene un n??mero.",
										specialChar: "La contrase??a tiene caracteres especiales.",
										match: "Las contrase??as coinciden.",
									}}
									
								/>
								
								<div class="columns">
									<input type="submit" value="REGISTRO" onClick={() => this.register()} />
									<input type="submit" value="VOLVER ATR??S" onClick={() => this.back()} />
								</div>
							</div>
						</div>
					</div>
					<div>

					</div>
				</div>

		)
	}
}

export default Register;