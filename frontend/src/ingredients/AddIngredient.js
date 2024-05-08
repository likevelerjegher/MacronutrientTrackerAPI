import axios from "axios";
import React, { useState } from 'react'
import { useNavigate } from "react-router";

export default function AddIngredient() {
    let navigate = useNavigate()

    const [ingredient, setIngredient] = useState([]);

    const [errors, setErrors] = useState({});

    const { name, weight, calories, fats, proteins, carbs } = ingredient;

    const validateInputs = () => {
        const errors = {};

        if (!name) {
            errors.name = 'Name is required';
        } else if (typeof name !== 'string') {
            errors.name = 'Name must be a string';
        }

        if (!weight) {
            errors.weight = 'Weight is required';
        } else if (!/^\d+$/.test(weight)) {
            errors.weight = 'Weight must be a valid integer';
        }

        if (!calories) {
            errors.calories = 'Calories are required';
        } else if (!/^\d+$/.test(calories)) {
            errors.calories = 'Calories must be a valid integer';
        }

        if (!fats) {
            errors.fats = 'Fats are required';
        } else if (!/^\d+(\.\d+)?$/.test(fats)) {
            errors.fats = 'Fats must be a valid float';
        }

        if (!proteins) {
            errors.proteins = 'Proteins are required';
        } else if (!/^\d+(\.\d+)?$/.test(proteins)) {
            errors.proteins = 'Proteins must be a valid float';
        }

        if (!carbs) {
            errors.carbs = 'Carbs are required';
        } else if (!/^\d+(\.\d+)?$/.test(carbs)) {
            errors.carbs = 'Carbs must be a valid float';
        }

        setErrors(errors);

        return Object.keys(errors).length === 0;
    };

    const onInputChange = (e) => {

        setIngredient({ ...ingredient, [e.target.name]: e.target.value })

    };
    const onSubmit = async (e) => {

        e.preventDefault();
        if (validateInputs()) {
            try {
                await axios.post("http://localhost:8080/api/ingredient", ingredient)
                navigate("/ingredients");
            } catch (error) {
                console.error("Error:", error);
            }
        }
        
    };
  return (
      <div className="container">
          <div className="row">
              <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                  <h2 className="text-center m-4">
                      Creating new ingredient
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
                          {errors.name && <div className="text-danger">{errors.name}</div>}
                      </div>
                      <div className="mb-3 text-left">
                          <label htmlFor="Servings" className="form-label">
                              Weight
                          </label>
                          <input
                              type={"text"} className="form-control"
                              placeholder="Enter the weight of the ingredient"
                              name="weight"
                              value={weight}
                              onChange={(e) => onInputChange(e)}
                          />
                          {errors.weight && <div className="text-danger">{errors.weight}</div>}

                      </div>
                      <div className="mb-3 text-left">
                          <label htmlFor="Calories" className="form-label">
                              Calories
                          </label>
                          <input
                              type={"text"} className="form-control"
                              placeholder="Enter the amount of calories"
                              name="calories"
                              value={calories}
                              onChange={(e) => onInputChange(e)}
                          />
                          {errors.calories && <div className="text-danger">{errors.calories}</div>}
                      </div>
                      <div className="mb-3 text-left">
                          <label htmlFor="Fats" className="form-label">
                              Fats
                          </label>
                          <input
                              type={"text"} className="form-control"
                              placeholder="Enter the amount of fats"
                              name="fats"
                              value={fats}
                              onChange={(e) => onInputChange(e)}
                          />
                          {errors.fats && <div className="text-danger">{errors.fats}</div>}
                      </div>
                      <div className="mb-3 text-left">
                          <label htmlFor="Carbs" className="form-label">
                              Carbs
                          </label>
                          <input
                              type={"text"} className="form-control"
                              placeholder="Enter the amount of carbs"
                              name="carbs"
                              value={carbs}
                              onChange={(e) => onInputChange(e)}
                          />
                          {errors.carbs && <div className="text-danger">{errors.carbs}</div>}
                      </div>
                      <div className="mb-3 text-left">
                          <label htmlFor="Proteins" className="form-label">
                              Proteins
                          </label>
                          <input
                              type={"text"} className="form-control"
                              placeholder="Enter the amount of proteins"
                              name="proteins"
                              value={proteins}
                              onChange={(e) => onInputChange(e)}
                          />
                          {errors.proteins && <div className="text-danger">{errors.proteins}</div>}
                      </div>
                      <button type="submit" className="btn btn-outline-dark">
                          Create
                      </button>
                  </form>
              </div>
          </div>
      </div>
  )
}
