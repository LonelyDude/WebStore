import React, {Component} from 'react'
import "./css/UserPage.css"
import notFound from "./img/notfound.png";

const Data=
    {
        image: notFound,
        surname: "Ублюдов",
        name:"Васян",
        patronymic:"Улдарович",
        email: "vasyyyan@mail.ru",
        country: "Россия",
        city: "Бутейко",
        birthday: "01.01.2000",
        contact: "+79995553434",
        history: [
            {
                product: notFound,
                description: "product",
                quantity: "2",
                price: "320",
                receiving: "20.11.2005"
            },
            {
                product: notFound,
                description: "product",
                quantity: "1",
                price: "340",
                receiving: "20.10.2005"
            },
            {
                product: notFound,
                description: "product",
                quantity: "1",
                price: "300",
                receiving: "25.10.2005"
            }]
    }

const UserItem = ({item}) =>
    <tr className="">
        <td>
            <img src={item.product}/>
        </td>
        <td>
            <p>
                {item.description}
            </p>
        </td>
        <td>
            {item.quantity}
        </td>
        <td>{item.price}</td>
        <td>{item.receiving}</td>
    </tr>


export default class UserPage extends Component {
    constructor(props) {
        super(props);

    }



    render() {

        return (
            <div className="container">
                <div id="main">
                    <div className="row" id="real-estates-detail">
                        <div className="col-lg-4 col-md-4 col-xs-12">
                            <div className="panel panel-default">
                                <div className="panel-heading">
                                    <header className="panel-title">
                                        <div className="text-center">
                                            <strong> Ваш профиль </strong>
                                        </div>
                                    </header>
                                </div>
                                <div className="panel-body">
                                    <div className="text-center" id="author">
                                        <img src={Data.image} alt=""/>
                                        <h3> {Data.name} </h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-8 col-md-8 col-xs-12">
                            <div className="panel">
                                <div className="panel-body">
                                    <div>
                                        <h4> Общая информация </h4>
                                        <table className="table table-th-block">
                                            <tbody>
                                            <tr>
                                                <td className="active">Фамилия:</td>
                                                <td><input type="text" name="agree" value={Data.surname} disabled="disabled"/></td>
                                            </tr>
                                            <tr>
                                                <td className="active">Имя:</td>
                                                <td><input type="text" name="agree" value={Data.name} disabled="disabled"/></td>
                                            </tr>
                                            <tr>
                                                <td className="active">Отчество:</td>
                                                <td><input type="text" name="agree" value={Data.patronymic} disabled="disabled"/></td>
                                            </tr>
                                            <tr>
                                                <td className="active">E-mail:</td>
                                                <td><input type="text" name="agree" value={Data.email} disabled="disabled"/></td>
                                            </tr>
                                            <tr>
                                                <td className="active">Страна:</td>
                                                <td><input type="text" name="agree" value={Data.country} disabled="disabled"/></td>
                                            </tr>
                                            <tr>
                                                <td className="active">Город:</td>
                                                <td><input type="text" name="agree" value={Data.city} disabled="disabled"/></td>
                                            </tr>

                                            <tr>
                                                <td className="active">Телефон:</td>
                                                <td><input type="text" name="agree" value={Data.contact} disabled="disabled"/></td>
                                            </tr>

                                            </tbody>
                                        </table>
                                        <input type="submit" name="submit" className="button" value="Редактировать"/>
                                    </div>
                                    <div>
                                        <h4>Ваша история заказов</h4>
                                        <div className=" history-orders">
                                            <table className=" table_blur">
                                                <tbody>
                                                <tr>
                                                    <td>Продукт</td>
                                                    <td>Описание</td>
                                                    <td>Количество</td>
                                                    <td>Цена</td>
                                                    <td>Дата получения</td>
                                                </tr>
                                                {Data.history.map((item)=><UserItem item={item}/>)}


                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

