import React, { useEffect, useState } from 'react';
import axios from "axios";
import { Link, useParams} from 'react-router-dom';
export default function Home() {

    const[dishes, setDishes]=useState([])
    const { id } = useParams()

    useEffect(()=> {
        loadDishes();
    },[]);

    const loadDishes=async()=>{
        const result=await axios.get("http://localhost:8080/api/dishes")
        setDishes(result.data);
    }

    const deleteDish=async(id)=>{
        await axios.delete(`http://localhost:8080/api/${id}`) 
        loadDishes()
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
                    <th scope="col">Servings</th>
                    <th scope="col">Fats</th>
                    <th scope="col">Carbs</th>
                    <th scope="col">Proteins</th>
                    <th scope="col"></th>
                </tr>
            </thead>
                <tbody className='table-group-divider'>

                    {
                        dishes.map((dish, index)=>(
                        <tr>
                            <th scope="row" key={index}>{index+1}</th>
                            <td>{dish.dishName}</td>
                            <td>{dish.dishCalories}</td>
                            <td>{dish.servings}</td>
                            <td>{dish.dishFats}</td>
                            <td>{dish.dishCarbs}</td>
                            <td>{dish.dishProteins}</td>
                            <td>
                                <div className="d-flex ">
                                    <Link className="align-self-start mr-auto btn btn-outline-dark"
                                            to={`/dishDescription/${dish.id}`}>
                                        View
                                    </Link>
                                    <Link className="btn btn-dark" to={`/updateDish/${dish.id}`}>
                                        Update
                                    </Link>
                                    <button className="btn btn-outline-dark"
                                        onClick={()=>deleteDish(dish.id)}>
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


