class Flower extends React.Component {
    render() {
        const  flowerInfo  = this.props.flowerInfo
        console.log(flowerInfo)
        const flowerPageUrl = "/flower?id=" + flowerInfo.id
        const flowerPrice = flowerInfo.price + "₽"
        const flowerImgPath = "/static/uploads/" + flowerInfo.imgName;
        return(
            <a href={flowerPageUrl} className="flower-url">
                <div className="list-flowers-item">
                    <div className="list-flowers-item-img">
                        <img src={flowerImgPath} width="200" height="200" />
                    </div>
                    <div className="list-flowers-item-info">
                        <div>
                            <p name="name">{flowerInfo.name}</p>
                        </div>
                        <div>
                            <p name="price">{flowerPrice}</p>
                        </div>
                    </div>

                </div>

            </a>
        )
    }
}
class FlowerList extends React.Component {
    render() {
        console.log(this.props.data)
        const flowerBlocks = this.props.data.map((flower)=> {
            return (
                <Flower flowerInfo={flower} />
            )
        })

        return(
            <div className="list-flowers">
                {flowerBlocks}
            </div>
            )
    }
}
class FlowerSearch extends React.Component {
    render() {
        return (
            <div className="search_params">
                <div>
                    <form>
                            <input type="text" name="FlowerName" onChange={this.props.changeValue}/>
                            <h2>Price range</h2>
                            <label for="min">Minimal</label>
                            <input type="number" id="min" name="min" min="0" onChange={this.props.changeMin}/>
                            <label for="max">Maximum</label>
                            <input type="number" id="max" name="max" max="100000" onChange={this.props.changeMax}/>
                    </form>
                </div>
            </div>

        )
    }
}
class App extends React.Component {
    initialState = {
        flowers: [],
        value: "",
        min: 0,
        max: 1000000
    }
    state = this.initialState

    componentDidMount() {
        const url = "http://localhost:8080/get-collection-flowers"
        fetch(url)
            .then(response => response.json())
            .then(result => this.setState({flowers: result}))
            .catch(e => console.log(e))

    }

    ChangeValue = (event) => {
        console.log(event.target.value)
        this.setState({value: event.target.value})
    }
    ChangeMin = (event) => {
        this.setState({min: event.target.value})
    }
    ChangeMax = (event) => {
        this.setState({max: event.target.value})
    }




    render() {
        const filteredFlowers = this.state.flowers.filter((flower) => {
            return (flower.name.toLowerCase().includes(this.state.value.toLowerCase()) && flower.price >= this.state.min && flower.price <= this.state.max);

        })
        return (
            <div className="flower-selection-block">
                <FlowerSearch changeValue={this.ChangeValue} changeMin={this.ChangeMin} changeMax={this.ChangeMax}/>
                <FlowerList data={filteredFlowers}/>
            </div>
        )




    }
}


ReactDOM.render(
    <App/>,
    document.getElementById('flower-selection-block')
)