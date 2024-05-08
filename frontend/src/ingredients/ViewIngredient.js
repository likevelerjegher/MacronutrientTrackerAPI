import axios from "axios";
import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router';
export default function ViewIngredient() {
    const [ingredient, setIngredient] = useState({
        name: "",
        weight: "",
        calories: "",
        fats: "",
        carbs: "",
        proteins: ""
    })

    const { id } = useParams();

    useEffect(() => {
        loadIngredient()
    }, [])

    const loadIngredient = async () => {
        const result = await axios.get(`http://localhost:8080/api/ingredients/${id}`);
        setIngredient(result.data);
    }


    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded  mt-2 shadow">
                    <h2 className="text-center m-4">
                        Ingredient description
                        <h4>
                            <div className="card mt-3 ">
                                <div className="card-header">
                                    <ul className="list-group list-group-flush ">
                                        <li className="list-group-item">
                                            <b>Name: </b>
                                            {ingredient.name}
                                        </li>
                                        <li className="list-group-item">
                                            <b>Weight: </b>
                                            {ingredient.weight}
                                        </li>
                                        <li className="list-group-item">
                                            <b>Calories: </b>
                                            {ingredient.calories}
                                        </li>
                                        <li className="list-group-item">
                                            <b>Fats: </b>
                                            {ingredient.fats}
                                        </li>
                                        <li className="list-group-item">
                                            <b>Carbs: </b>
                                            {ingredient.carbs}
                                        </li>
                                        <li className="list-group-item">
                                            <b>Proteins: </b>
                                            {ingredient.proteins}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </h4>
                    </h2>
                </div>
            </div>
        </div>
    );
}
