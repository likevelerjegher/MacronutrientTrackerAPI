import axios from "axios";
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from "react-router";

export default function EditDish() {

    let navigate = useNavigate();
    const { id } = useParams();

    const [ingredient, setIngredient] = useState({
        name: "",
        weight: "",
        calories: "",
        fats: "",
        carbs: "",
        proteins: ""
    })

    const { name, weight, calories, fats, proteins, carbs } = ingredient;

    const onInputChange = (e) => {

        setIngredient({ ...ingredient, [e.target.name]: e.target.value });

    };

    useEffect(() => {
        loadIngredient()
    }, []);

    const onSubmit = async (e) => {

        e.preventDefault();
        await axios.put(`http://localhost:8080/api/ingredient/${id}`, ingredient);
        navigate("/ingredients");
    };

    const loadIngredient = async () => {
        const result = await axios.get(`http://localhost:8080/api/ingredients/${id}`)
        setIngredient(result.data)
    };

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className="text-center m-4">
                        Updating the ingredient
                    </h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3 text-left">
                            <label htmlFor="Name" className="form-label">
                                 Name
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the name of the ingredient"
                                name="name"
                                value={name}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3 text-left">
                            <label htmlFor="Weight" className="form-label">
                               Weight
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the weight"
                                name="weight"
                                value={weight}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3 text-left">
                            <label htmlFor="Calories" className="form-label">
                                Calories
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the calories"
                                name="calories"
                                value={calories}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3 text-left">
                            <label htmlFor="Fats" className="form-label">
                                Fats
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the fats"
                                name="fats"
                                value={fats}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3 text-left">
                            <label htmlFor="Carbs" className="form-label">
                                Carbs
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the carbs"
                                name="carbs"
                                value={carbs}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3 text-left">
                            <label htmlFor="Proteins" className="form-label">
                                Proteins
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the proteins"
                                name="proteins"
                                value={proteins}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <button type="submit" className="btn btn-outline-dark">
                            Update
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}
