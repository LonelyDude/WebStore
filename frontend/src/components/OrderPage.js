import React, {Component} from 'react'
import "./css/OrderPage.css"
import notFound from "./img/notfound.png";

const Cross = () =>
    <svg xmlns="http://www.w3.org/2000/svg" version="1.1" viewBox="0 0 21.9 21.9" width="25px" height="25px">
        <path d="M14.1,11.3c-0.2-0.2-0.2-0.5,0-0.7l7.5-7.5c0.2-0.2,0.3-0.5,0.3-0.7s-0.1-0.5-0.3-0.7l-1.4-1.4C20,0.1,19.7,0,19.5,0  c-0.3,0-0.5,0.1-0.7,0.3l-7.5,7.5c-0.2,0.2-0.5,0.2-0.7,0L3.1,0.3C2.9,0.1,2.6,0,2.4,0S1.9,0.1,1.7,0.3L0.3,1.7C0.1,1.9,0,2.2,0,2.4  s0.1,0.5,0.3,0.7l7.5,7.5c0.2,0.2,0.2,0.5,0,0.7l-7.5,7.5C0.1,19,0,19.3,0,19.5s0.1,0.5,0.3,0.7l1.4,1.4c0.2,0.2,0.5,0.3,0.7,0.3  s0.5-0.1,0.7-0.3l7.5-7.5c0.2-0.2,0.5-0.2,0.7,0l7.5,7.5c0.2,0.2,0.5,0.3,0.7,0.3s0.5-0.1,0.7-0.3l1.4-1.4c0.2-0.2,0.3-0.5,0.3-0.7  s-0.1-0.5-0.3-0.7L14.1,11.3z" fill="#bfbfbf"/>
    </svg>

const Minus = () =>
    <svg xmlns="http://www.w3.org/2000/svg" version="1.1" x="0px" y="0px" viewBox="0 0 52 52" width="40px" height="40px">
        <g>
            <path d="M26,0C11.664,0,0,11.663,0,26s11.664,26,26,26s26-11.663,26-26S40.336,0,26,0z M26,50C12.767,50,2,39.233,2,26   S12.767,2,26,2s24,10.767,24,24S39.233,50,26,50z" fill="#bfbfbf"/>
            <path d="M39,25H13c-0.552,0-1,0.447-1,1s0.448,1,1,1h26c0.552,0,1-0.447,1-1S39.552,25,39,25z" fill="#bfbfbf"/>
        </g>
    </svg>

const Plus = () =>
    <svg xmlns="http://www.w3.org/2000/svg" version="1.1" id="Capa_1" x="0px" y="0px" viewBox="0 0 52 52" width="40px" height="40px">
        <g>
            <path d="M26,0C11.664,0,0,11.663,0,26s11.664,26,26,26s26-11.663,26-26S40.336,0,26,0z M26,50C12.767,50,2,39.233,2,26   S12.767,2,26,2s24,10.767,24,24S39.233,50,26,50z" fill="#bfbfbf"/>
            <path d="M38.5,25H27V14c0-0.553-0.448-1-1-1s-1,0.447-1,1v11H13.5c-0.552,0-1,0.447-1,1s0.448,1,1,1H25v12c0,0.553,0.448,1,1,1   s1-0.447,1-1V27h11.5c0.552,0,1-0.447,1-1S39.052,25,38.5,25z" fill="#bfbfbf"/>
        </g>
    </svg>


class ShoppingCartItem extends Component {
    constructor(props){
        super(props);

        /* вызов метода запроса полной инфы о товаре по id, сохранение результата в переменную productInfo */
        /* let productInfo = ... */
        // const {id, name, manufacturer, cost, images} = productInfo;

        this.state = {
            quantity: 1,
            itemCountSum: 100 /* props.cost */
        };
        this.quantityAdd = this.quantityAdd.bind(this);
        this.quantityReduce = this.quantityReduce.bind(this);
    }

    quantityAdd(){
        if (this.state.quantity !== 50) {
            this.setState({
                quantity: ++this.state.quantity,
                itemCountSum: this.state.quantity * 100 /* cost */
            });
        }
    }

    quantityReduce(){
        if (this.state.quantity !== 1) {
            this.setState({
                quantity: --this.state.quantity,
                itemCountSum: this.state.quantity * 100 /* cost */
            });
        }
    }

    render() { /* {item,i} */
        return(
            <tr className="shopping-cart-item"> {/* key={i} */}
                <td>
                    <img src={notFound} alt=""/>
                </td>
                <td className="description">
                    <p>
                        <span>Степлер</span><br/>
                        <span>ООО "Канцеляр"</span><br/>
                        <span>Синий</span>
                    </p>
                </td>

                <td className="quantity">
                    <div onClick={this.quantityAdd}>
                        <Plus />
                    </div>
                    <p>{this.state.quantity}</p>
                    <div onClick={this.quantityReduce}>
                        <Minus />
                    </div>
                </td>

                <td className="total-price">{ this.state.itemCountSum } руб.</td>

                <td>
                    <Cross />
                </td>
            </tr>
        );
    }
}



export default class OrderPage extends Component{
    constructor(){
        super();
    }

    render() {
        return (
            <div className="order-container">
                <div>

                    <h1>Ваша корзина</h1>

                    <table>
                        <tbody>
                            <tr className="hat">
                                <td>Продукт</td>
                                <td>Описание</td>
                                <td>Количество</td>
                                <td>Цена</td>
                                <td></td>
                            </tr>

                            <ShoppingCartItem />
                            <ShoppingCartItem />
                            <ShoppingCartItem />

                        </tbody>
                    </table>

                    <div className="order-accept-block">
                        <div className="float-right">
                            <p id="goods-cost">Стоимость товаров: <span>1300</span></p>
                            <p id="deliver-price">Стоимость доставки: <span>450</span></p>
                            <p id="total-sum">Итого: <span>1750</span></p>
                            <button className="button">Оформить заказ</button>
                        </div>
                        <div className="clear-float"></div>
                    </div>

                </div>
            </div>
        );
    }
}
