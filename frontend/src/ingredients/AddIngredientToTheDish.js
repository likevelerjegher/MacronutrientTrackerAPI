import React, { useEffect, useState } from 'react';
import axios from "axios";
import { Link, useParams, useNavigate } from 'react-router-dom';

export default function AddIngredientToTheDish() {
    let navigate = useNavigate()

    const[ingredient, setIngredients]=useState([]);
    const { id } = useParams();

    useEffect(()=> {
        loadIngredients();
    },[]);

    const loadIngredients=async()=>{
        const result = await axios.get("http://localhost:8080/api/ingredients");
        setIngredients(result.data);
    }
    const onSubmit = async (e, ingredientId) => {

        e.preventDefault();
        await axios.post(`http://localhost:8080/api/dishes/${id}/ingredients/${ingredientId}`);
        navigate(`/dishDescription/${id}`);
    };

  return (
      <div className='container'>
          <div className=''>
              <h2 className="text-center m-4">
                  Add ingredient to the dish
              </h2>
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
                                                <button type="submit" className="btn btn-outline-dark"
                                                    onClick={(e) => onSubmit(e, ingredient.id)}>
                                                    ADD
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
