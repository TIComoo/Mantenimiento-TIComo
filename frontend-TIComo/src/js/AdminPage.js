import * as React from 'react';
import '../css/StylePage.css';
import { Component } from 'react';
import { authenticationService } from '../services/authentication-service';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Table from 'react-bootstrap/Table';
import { Link } from 'react-router-dom';
import { IMAGES, ROUTES } from '../components/constants';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from '@fortawesome/free-solid-svg-icons'
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons'

const starsArray = [
	"#f17a45",
	"#f19745",
	"#f1a545",
	"#f1b345",
	"#f1d045",
];

/* FUNCTIONS */
function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`full-width-tabpanel-${index}`}
            aria-labelledby={`full-width-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 4 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}

class AdminPage extends Component {
    constructor(props) {
        super(props);
    }

    state = {
        admins: [], riders: [], clients: [], restaurants: [],
        value: (this.props.location.value == undefined) ? 2 : this.props.location.value
    }

    /* INITIALIZER */
    componentDidMount() {
        fetch(ROUTES.PROXY + '/admin/showAllAdmins', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        }).then(data => {
            this.setState({ admins: data })
        }).catch((err) => {
            console.log(err);
        })

        fetch(ROUTES.PROXY + '/rider/showAllRiders', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        }).then(data => {
            this.setState({ riders: data })
        }).catch((err) => {
            console.log(err);
        })

        fetch(ROUTES.PROXY + '/client/showAllClients', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        }).then(data => {
            this.setState({ clients: data })
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

    goToAdmin(adminID) {
        this.props.history.push({
            pathname: '/admin/consultAdmin/' + adminID,
        });
    }

    goToRider(riderID) {
        this.props.history.push({
            pathname: '/admin/consultRider/' + riderID,
        });
    }
    
    goToClient(clientID) {
        this.props.history.push({
            pathname: '/admin/consultClient/' + clientID,
        });
    }
    
    goToRestaurant(restaurantID) {
        this.props.history.push({
            pathname: '/admin/consultRestaurant/' + restaurantID,
        });
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
    AdminsTable = (data) => {
        let counter = 1;
        const tableRows = data.map(
            (element) => {
                return (
                    <tr>
                        <td>{counter++}</td>
                        <td>
                            <button
                                className="button-custom"
                                onClick={() => this.goToAdmin(element.id)}
                            >
                                {element.email}
                            </button>
                        </td>
                    </tr>
                )
            }
        )
        return (
                <Table striped bordered hover variant="light">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tableRows}
                    </tbody>
                </Table>
        );
    }

    RidersTable = (data) => {
        let counter = 1;
        const tableRows = data.map(
            (element) => {
                return (
                    <tr>
                        <td>{counter++}</td>
                        <td>
                            <button
                                className="button-custom"
                                onClick={() => this.goToRider(element.id)}
                            >
                                {element.email}
                            </button>
                        </td>
                    </tr>
                )
            }
        )
        return (
                <Table striped bordered hover variant="light">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tableRows}
                    </tbody>
                </Table>
        );
    }

    ClientsTable = (data) => {
        let counter = 1;
        const tableRows = data.map(
            (element) => {
                return (
                    <tr>
                        <td>{counter++}</td>
                        <td>
                        <button
                                className="button-custom"
                                onClick={() => this.goToClient(element.id)}
                            >
                                {element.email}
                            </button>
                        </td>
                    </tr>
                )
            }
        )
        return (
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    {tableRows}
                </tbody>
            </Table>
        );
    }

    RestaurantsTable = (data) => {
        let counter = 1;
        const tableRows = data.map(
            (element) => {
                return (
                    <tr>
                        <td>{counter++}</td>
                        <td>
                        <button
                                className="button-custom"
                                onClick={() => this.goToRestaurant(element.id)}
                            >
                                {element.name}
                            </button>
                        </td>
                        <td>
                            {this.roundAverage(element.averageRate)}<FontAwesomeIcon icon={faStar} font-size={20} color={this.rateColour(element.averageRate)} />
                        </td>
                    </tr>
                )
            }
        )
        return (
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nombre</th>
                        <th>Valoración</th>
                    </tr>
                </thead>
                <tbody>
                    {tableRows}
                </tbody>
            </Table>
        );
    }

    /* EVENTS */
    logout() {
        authenticationService.logout();
        this.props.history.push(ROUTES.LOGIN);
    }

    handleTabChange = (event, newValue) => {
        this.setState({ value: newValue })
    };

    render() {
        return (
            <div class="center4" auto >
            <div class="card4" auto >  
            <><Box
            >
                <Tabs
                        orientation='vertical'
                        variant="fullwidth"
                        value={this.state.value}
                        onChange={this.handleTabChange}
                        aria-label="full width tabs example"
                >
                        <img src={IMAGES.FONDO_TRANSPARENTE} className="logo1" width="200" height="200" />
                        <img src={IMAGES.LOGO_FONDO} className="logo1" width="160" height="90" alt="" />
                        <Tab label={<span class="rider">Administradores</span>} {...a11yProps(2)} />
                        <Tab label={<span class="rider">Riders</span>} {...a11yProps(3)} />
                        <Tab label={<span class="rider">Clientes</span>} {...a11yProps(4)} />
                        <Tab label={<span class="rider">Restaurantes</span>} {...a11yProps(4)} />

                        <FontAwesomeIcon icon={faRightFromBracket} font-size={30} color={"#61412d"} onClick={() => this.logout()} />
                </Tabs>

                <TabPanel value={this.state.value} index={2}>
                    <h2>GESTIÓN DE ADMINS</h2>
                    {this.AdminsTable(this.state.admins)}
                    <Link to={"/admin/addAdmin"}>Añadir un nuevo administrador</Link>
                </TabPanel>

                <TabPanel value={this.state.value} index={3}>
                    <h2>GESTIÓN DE RIDERS</h2>
                    {this.RidersTable(this.state.riders)}
                    <Link to={"/admin/addRider"}>Añadir un nuevo rider</Link>
                </TabPanel>

                <TabPanel value={this.state.value} index={4}>
                    <h2>GESTIÓN DE CLIENTES</h2>
                    {this.ClientsTable(this.state.clients)}
                </TabPanel>

                <TabPanel value={this.state.value} index={5}>
                    <h2>GESTIÓN DE RESTAURANTES</h2>
                    {this.RestaurantsTable(this.state.restaurants)}
                    <Link to={"/admin/addRestaurant"}>Añadir un nuevo restaurante</Link>
                </TabPanel>

            </Box>
</>
                </div>
            </div>
        )
    }

}

export default AdminPage;