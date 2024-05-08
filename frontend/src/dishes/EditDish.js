import axios from "axios";
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from "react-router";

export default function EditDish() {

    let navigate = useNavigate();
    const {id}=useParams();

    const [dish, setDish] = useState({
        dishName: "",
        servings: ""
    });

    const { dishName, servings } = dish;

    const onInputChange = (e) => {

        setDish({ ...dish, [e.target.name]: e.target.value });

    };

    useEffect (()=>{
        loadDish()
    }, []);

    const onSubmit = async (e) => {

        e.preventDefault();
        await axios.put(`http://localhost:8080/api/${id}`, dish);
        navigate("/");
    };

    const loadDish =async ()=>{
        const result = await axios.get(`http://localhost:8080/api/${id}`)
        setDish(result.data)
    };

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className="text-center m-4">
                        Updating the dish
                    </h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3 text-left">
                            <label htmlFor="Name" className="form-label">
                                Dish name
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the name of the dish"
                                name="dishName"
                                value={dishName}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3 text-left">
                            <label htmlFor="Servings" className="form-label">
                                Number of servings
                            </label>
                            <input
                                type={"text"} className="form-control"
                                placeholder="Enter the number of servings"
                                name="servings"
                                value={servings}
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
