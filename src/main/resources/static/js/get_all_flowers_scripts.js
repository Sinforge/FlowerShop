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
    initialState = {
        flowers: [
        ]
    }
    state = this.initialState
    componentDidMount() {
        const url = "http://localhost:8080/get-collection-flowers"
        fetch(url)
            .then(response => response.json())
            .then(result => this.setState({flowers: result}))
            .catch(e => console.log(e))

    }
    render() {
        console.log(this.state.flowers)
        const flowerBlocks = this.state.flowers.map((flower)=> {
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

    ReactDOM.render(
        <FlowerList/>,
        document.getElementById('flower-container')
    );
