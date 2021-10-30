import React, { useState } from 'react'
import productos from '../productos'

const ProductsList = () => {

    const [prods, setProductos] = useState(productos);

    return (
        <div className="App">
            <table className="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        prods.map(elemento => {
                            <tr>
                                <td>{elemento.id}</td>
                                <td>{elemento.nombre}</td>
                                <td>{elemento.precio}</td>
                            </tr>
                        })
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ProductsList

