import * as React from 'react';
import { Component } from 'react';
import { authenticationService } from '../services/authentication-service';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Table from 'react-bootstrap/Table';
import { IMAGES, ROUTES } from '../components/constants';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons'

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
                <Box sx={{ p: 1 }}>
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

class AtencionPage extends Component {
    constructor(props) {
        super(props);
    }

    state = {
        clients: [],
        value: (this.props.location.value == undefined) ? 2 : this.props.location.value
    }

    /* INITIALIZER */
    componentDidMount() {

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
    }
    
    goToClient(clientID) {
        this.props.history.push({
            pathname: '/atencion/showAllOrdersByClient/' + clientID,
        });
    }

    /* TABLES */
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
            <div style={{ width: "695px" }}>
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
            </div>
        );
    }

    /* EVENTS */
    logout() {
        authenticationService.logout();
        this.props.history.push(ROUTES.LOGIN);
    }

    render() {
        return (
            <><Box
                sx={{ flexGrow: 1, bgcolor: 'white', height: 800, display: 'flex', width: 1000 }}
            >
                <Tabs
                    orientation='vertical'
                    variant="fullwidth"
                    value={this.state.value}
                    aria-label="full width tabs example"
                    TabIndicatorProps={{ sx: { backgroundColor: '#D6C2B5' } }}
                    sx={{ borderRight: 1, borderColor: 'divider', backgroundColor: '#3F322B', width: 250 }}
                    textColor="white"
                    centered
                >
                    <img src={IMAGES.FONDO_TRANSPARENTE} className="logo" width="160" height="50" alt="" />
                    <img src={IMAGES.LOGO_FONDO} className="logo" width="160" height="90" alt="" />
                    <Tab label={<span style={{ color: '#D6C2B5' }}>Clientes</span>} {...a11yProps(2)} />
                    <FontAwesomeIcon icon={faRightFromBracket} font-size={20} color={"#D6C2B5"} onClick={() => this.logout()} />
                </Tabs>

                <TabPanel value={this.state.value} index={2}>
                    <h2>CLIENTES</h2>
                    {this.ClientsTable(this.state.clients)}
                </TabPanel>

            </Box>
                <div>
                    <input type="button" value="LOGOUT" onClick={() => this.logout()} />
                </div></>
        )
    }

}

export default AtencionPage;