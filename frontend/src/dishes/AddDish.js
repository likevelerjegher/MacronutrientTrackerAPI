import axios from "axios";
import React, { useState } from 'react'
import { useNavigate } from "react-router";

export default function AddDish() {

    let navigate=useNavigate()

    const [dish,setDish]=useState({
        dishName:"",
        servings:""
    })

    const [errors, setErrors] = useState({});

    const{dishName, servings}=dish;

    const validateInputs = () => {
        const errors = {};

        if (!dishName) {
            errors.dishName = 'Dish name is required';
        } else if (typeof dishName !== 'string') {
            errors.dishName = 'Dish name must be a string';
        }

        if (!servings) {
            errors.servings = 'Number of servings is required';
        } else if (!/^\d+$/.test(servings)) {
            errors.servings = 'Servings must be a valid integer';
        }

        setErrors(errors);

        return Object.keys(errors).length === 0;
    };

    const onInputChange=(e)=>{

        setDish({...dish, [e.target.name]:e.target.value})

    };
    const onSubmit=async (e)=>{

        e.preventDefault();
        if (validateInputs()) {
            try {
                await axios.post("http://localhost:8080/api", dish);
                navigate("/");
            } catch (error) {
                console.error("Error:", error);
            }
        }
    };

  return(
    <div className="container">
        <div className="row">
            <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                <h2 className="text-center m-4">
                    Creating new dish
                </h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                    <div className="mb-3 text-left">
                        <label htmlFor="Name" className="form-label">
                            Name
                        </label>
                        <input
                            type={"text"} className="form-control"
                            placeholder="Enter the name of the dish"
                            name="dishName"
                            value={dishName}
                            onChange={(e)=>onInputChange(e)}
                            />
                          {errors.dishName && <div className="text-danger">{errors.dishName}</div>}
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
                          {errors.servings && <div className="text-danger">{errors.servings}</div>}
                    </div>
                    <button type="submit" className="btn btn-outline-dark">
                        Create
                    </button>
                </form>
            </div>
        </div>
    </div>
  );
}