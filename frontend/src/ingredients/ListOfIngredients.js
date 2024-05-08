import React, { useEffect, useState } from 'react';
import axios from "axios";
import { Link, useParams } from 'react-router-dom';

export default function ListOfIngredients() {

    const[ingredient, setIngredients]=useState([]);
    const { id } = useParams();

    useEffect(()=> {
        loadIngredients();
    },[]);

    const loadIngredients=async()=>{
        const result = await axios.get("http://localhost:8080/api/ingredients");
        setIngredients(result.data);
    }

    const deleteIngredient=async(id)=>{
        await axios.delete(`http://localhost:8080/api/ingredients/${id}`) ;
        loadIngredients();
    }

    return (
        <div className='container'>
            <div className=''>
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
                            ingredient.map((ingredient, index) => (
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
                                            <Link className="align-self-start mr-auto btn btn-outline-dark"
                                                to={`/ingredientDescription/${ingredient.id}`}>
                                                View
                                            </Link>
                                            <Link className="btn btn-dark" to={`/updateIngredient/${ingredient.id}`}>
                                                Update
                                            </Link>
                                            <button className="btn btn-outline-dark"
                                                onClick={() => deleteIngredient(ingredient.id)}>
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
  )
}
