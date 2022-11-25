class Flower extends React.Component {
    render() {
        const  flowerInfo  = this.props.flowerInfo.flower
        console.log(flowerInfo)
        const flowerPageUrl = "/flower?id=" + flowerInfo.id
        const flowerPrice = flowerInfo.price + "₽"
        const flowerImgPath = "/static/uploads/" + flowerInfo.imgname;
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
                        <div class="text-field">
                            <label for="FlowerName">Search</label>
                            <input class="input-data"  type="text" name="FlowerName" onChange={this.props.changeValue}/>
                        </div>
                        <div class="text-field">
                            <label htmlFor="min">Minimal</label>
                            <input class="input-data"  type="number" id="min" name="min" min="0" onChange={this.props.changeMin}/>
                        </div>
                        <div className="text-field">
                            <label htmlFor="max">Maximum</label>
                            <input class="input-data"  type="number" id="max" name="max" max="100000" onChange={this.props.changeMax}/>
                        </div>




                    </form>
                    <button class onClick={this.props.sortUpFunction}>Sort by rating down</button>
                    <button onClick={this.props.sortDownFunction}>Sort by rating up</button>
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
        max: 1000000,
        sortUpRating: false,
        sortDownRating: false,
    }
    state = this.initialState

    componentDidMount() {
        const url = "http://localhost:8080/get-collection-flowers"
        fetch(url)
            .then(response => response.json())
            .then(result => {
                this.setState({flowers: result});
            })
            .catch(e => console.log(e))

    }

    ChangeValue = (event) => {
        this.setState({value: event.target.value})
    }
    ChangeMin = (event) => {
        this.setState({min: event.target.value})
    }
    ChangeMax = (event) => {
        this.setState({max: event.target.value})
    }
    SortUp = (event) => {
        this.setState({sortUpRating: !this.state.sortUpRating})
    }
    SortDown = (event) => {
        this.setState({sortDownRating: !this.state.sortDownRating});
    }




    render() {
        const filteredFlowers = this.state.flowers.filter((flower) => {
            return (flower.flower.name.toLowerCase().includes(this.state.value.toLowerCase()) && flower.flower.price >= this.state.min && flower.flower.price <= this.state.max);

        })
        for (let i= 0 ; i<filteredFlowers.length;i++) {
            console.log(filteredFlowers[i] === 'NaN', filteredFlowers[i]);
        }
        if(this.state.sortUpRating) {
            filteredFlowers.sort((a, b) => {
                let aRating = Number.isNaN(a.rating)?0:Number(a.rating);
                let bRating = Number.isNaN(b.rating)?0:Number(b.rating);
               return aRating - bRating;
            });
        }
        if(this.state.sortDownRating) {
            filteredFlowers.sort((a, b) => {
                let aRating = Number.isNaN(a.rating)?0:Number(a.rating);
                let bRating = Number.isNaN(b.rating)?0:Number(b.rating);
                return bRating - aRating;
            });
        }
        return (
            <div className="flower-selection-block">
                <FlowerSearch changeValue={this.ChangeValue} changeMin={this.ChangeMin} changeMax={this.ChangeMax}
                              sortUpFunction={this.SortUp} sortDownFunction={this.SortDown}/>
                <FlowerList data={filteredFlowers}/>
            </div>
        )




    }
}


ReactDOM.render(
    <App/>,
    document.getElementById('flower-selection-block')
)