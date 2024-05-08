import axios from "axios";
import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';

export default function ViewDish() {

    const [dish,setDish] = useState({
        dishName:"",
        servings:"",
        dishCalories:"",
        dishFats: "",
        dishCarbs:"",
        dishProteins: "",
   
    })
    const [ingredients, setIngredients] = useState([]);


    const { id }=useParams();
    const { ingredientId } = useParams();

    useEffect(()=>{
        loadDish();
        loadIngredients();
    }, [])

    const loadDish = async () => {
        const result = await axios.get(`http://localhost:8080/api/${id}`)
        setDish(result.data)
    };

    const loadIngredients = async () => {
        const result = await axios.get(`http://localhost:8080/api/dishes/${id}/ingredients`);
        setIngredients(result.data);
    }

    const deleteIngredient = async (id, ingredientId) => {
        await axios.delete(`http://localhost:8080/api/dishes/${id}/ingredients/${ingredientId}`);
        loadIngredients();
        loadDish();

    }


  return (
    <div className="container">
        <div className="row">
            <div className="col-md-6 offset-md-3 border rounded  mt-2 shadow">
                <h2 className="text-center m-4">
                    Dish description
                    <h4>
                        <div className="card mt-3 ">
                            <div className="card-header">
                                Dish details
                                <ul className="list-group list-group-flush ">
                                    <li className="list-group-item">
                                        <b>Name: </b>
                                        {dish.dishName}
                                    </li>
                                    <li className="list-group-item">
                                        <b>Servings: </b>
                                        {dish.servings}
                                    </li> 
                                    <li className="list-group-item">
                                        <b>Calories: </b>
                                        {dish.dishCalories}
                                    </li>  
                                    <li className="list-group-item">
                                        <b>Fats: </b>
                                        {dish.dishFats}
                                    </li>
                                    <li className="list-group-item">
                                        <b>Carbs: </b>
                                        {dish.dishCarbs}
                                    </li>
                                    <li className="list-group-item">
                                        <b>Proteins: </b>
                                        {dish.dishProteins}
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </h4>
                    Ingredients:
                </h2>
                  <Link className="btn btn-dark" to={`/addIngredietntToDish/${dish.id}`}>
                      Add ingredient to the dish
                  </Link>
                    <table className="table border shadow rounded p-4 mt-2">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Name</th>
                                <th scope="col">Calories</th>
                                <th scope="col">Weight</th>
                                <th scope="col">Fats</th>
                                <th scope="col">Carbs</th>
                                <th scope="col">Proteins</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody className='table-group-divider'>
                            {
                                ingredients.map((ingredient, index) => (
                                    <tr>
                                        <th scope="row" key={index}>{index + 1}</th>
                                        <td>{ingredient.name}</td>
                                        <td>{ingredient.calories}</td>
                                        <td>{ingredient.weight}</td>
                                        <td>{ingredient.fats}</td>
                                        <td>{ingredient.carbs}</td>
                                        <td>{ingredient.proteins}</td>
                                        <td>
                                            <div className="d-flex ">
                                                <button className="btn btn-outline-dark"
                                                    onClick={() => deleteIngredient(dish.id, ingredient.id)}>
                                                    Delete
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                ))
                            }

                        </tbody>
                    </table>
            </div>
        </div>
    </div>
  );
}
